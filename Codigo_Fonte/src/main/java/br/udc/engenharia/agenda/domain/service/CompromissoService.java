package br.udc.engenharia.agenda.domain.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import org.directwebremoting.annotations.RemoteProxy;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.udc.engenharia.agenda.application.security.ContextHolder;
import br.udc.engenharia.agenda.domain.entity.EventoLog;
import br.udc.engenharia.agenda.domain.entity.account.User;
import br.udc.engenharia.agenda.domain.entity.compromisso.Agenda;
import br.udc.engenharia.agenda.domain.entity.compromisso.CategoriaCompromisso;
import br.udc.engenharia.agenda.domain.entity.compromisso.CompartilhamentoCompromisso;
import br.udc.engenharia.agenda.domain.entity.compromisso.Compromisso;
import br.udc.engenharia.agenda.domain.entity.compromisso.CompromissoFrenquencia;
import br.udc.engenharia.agenda.domain.entity.compromisso.TipoCompromisso;
import br.udc.engenharia.agenda.domain.repository.IAgendaRepository;
import br.udc.engenharia.agenda.domain.repository.ICategoriaCompromissoRepository;
import br.udc.engenharia.agenda.domain.repository.ICompartilhamentoCompromissoRepository;
import br.udc.engenharia.agenda.domain.repository.ICompromissoRepository;
import br.udc.engenharia.agenda.domain.repository.ITipoCompromissoRepository;
import br.udc.engenharia.agenda.infrastructure.mail.WarningEmailSender;

/**
 * 
 * @author Henrique L. Zago
 */
@Service
@Transactional
@RemoteProxy(name = "compromissoService")
public class CompromissoService
{

	/**
	 * 
	 */
	@Autowired
	private ICompromissoRepository compromissoRepository;

	/**
	 * 
	 */
	@Autowired
	private IAgendaRepository agendaRepository;

	/**
	 * 
	 */
	@Autowired
	private ITipoCompromissoRepository tipoCompromissoRepository;

	/**
	 * 
	 */
	@Autowired
	private ICategoriaCompromissoRepository categoriaCompromissoRepository;

	/**
	 * 
	 */
	@Autowired
	private ICompartilhamentoCompromissoRepository compartilhamentoCompromissoRepository;

	/**
	 * 
	 */
	@Autowired
	private LoggingService loggingService;

	/**
	 * 
	 * @param compromisso
	 * @return
	 */
	public Compromisso insertCompromisso( Compromisso compromisso )
	{
		Assert.notNull( compromisso );

		User usuario = ContextHolder.getAuthenticatedUser();

		compromisso.setUsuario( usuario );

		this.arrangeAgenda( compromisso );

		String log = "Usuário " + usuario.getName() + " cadastrou o compromisso " + compromisso.getTitulo();
		EventoLog eventoLog = new EventoLog( log, usuario );
		this.loggingService.insertEventoLog( eventoLog );
		
//		this.loggingService.recordTextFile( usuario.getName(), "Usuário " + usuario.getName() + " cadastrou o compromisso " + compromisso.getTitulo() );

		compromisso = this.compromissoRepository.save( compromisso );
		Assert.notNull( compromisso, "Erro ao salvar o compromisso" );

		this.scheduleCompromissoWarningEmail( compromisso );

		return compromisso;
	}

	/**
	 * 
	 * @param compromisso
	 * @return
	 */
	public Compromisso updateCompromisso( Compromisso compromisso )
	{
		Assert.notNull( compromisso );

		User usuario = ContextHolder.getAuthenticatedUser();

		compromisso.setUsuario( usuario );

		this.clearAgenda( compromisso );
		this.arrangeAgenda( compromisso );

		String log = "Usuário " + usuario.getName() + " atualizou o compromisso " + compromisso.getTitulo();
		EventoLog eventoLog = new EventoLog( log, usuario );
		this.loggingService.insertEventoLog( eventoLog );
		
//		this.loggingService.recordTextFile( usuario.getName(), "Usuário " + usuario.getName() + " atualizou o compromisso " + compromisso.getTitulo() );

		return this.compromissoRepository.save( compromisso );
	}

	/**
	 * 
	 * @param filter
	 * @param pageable
	 * @return
	 */
	public List<Compromisso> listCompromissosByFilters( String filter, PageRequest pageable )
	{
		Long userId = ContextHolder.getAuthenticatedUser().getId();

		List<Compromisso> compromissos = this.compromissoRepository.listByFilters( userId );

		for ( CompartilhamentoCompromisso compartilhamentoCompromisso : this.compartilhamentoCompromissoRepository.listShared( userId ) )
		{
			Compromisso compromisso = compartilhamentoCompromisso.getCompromisso();
			compromisso.setShared( true );
			compromissos.add( compromisso );
		}

		for ( Compromisso compromisso : compromissos )
		{
			final List<Agenda> agendas = this.agendaRepository.listByCompromisso( compromisso.getId() );

			compromisso.setAgendas( agendas );
		}

		return compromissos;
	}
	/**
	 * 
	 * @param filter
	 * @param pageable
	 * @return
	 */
	public List<Agenda> listAgendasByFilters( String titulo, Date dataInicio, Date dataFim, Long tipoCompromissoId, Long categoriaCompromissoId )
	{
		System.out.println("Data Inicio: " + dataInicio);
		System.out.println("Data Fim: " + dataFim);
		return this.agendaRepository.listByFilters( titulo, dataInicio, dataFim, categoriaCompromissoId, tipoCompromissoId );
	}

	/**
	 * 
	 * @param compromisso
	 */
	private void arrangeAgenda( Compromisso compromisso )
	{
		User usuario = ContextHolder.getAuthenticatedUser();

		List<Agenda> agendas = new ArrayList<Agenda>();

		if ( compromisso.getFrequencia().equals( CompromissoFrenquencia.UMA_VEZ ) )
		{
			agendas.add( new Agenda( compromisso.getDataInicio(), compromisso.getDataFim(), usuario, compromisso ) );
		}
		else if ( compromisso.getFrequencia() == CompromissoFrenquencia.DIARIAMENTE )
		{

			DateTime finalDateReference = new DateTime( compromisso.getDataFim() );

			DateTime finalDate = finalDateReference.withYear( finalDateReference.getYear() + 1 );

			for ( DateTime dateBegin = new DateTime( compromisso.getDataInicio() ); dateBegin.isBefore( finalDate ); dateBegin = dateBegin.plusDays( 1 ) )
			{
				Interval interval = new Interval( new DateTime( compromisso.getDataInicio() ), dateBegin );

				DateTime dateEnd = finalDateReference.plus( interval.toDurationMillis() );

				agendas.add( new Agenda( dateBegin.toDate(), dateEnd.toDate(), usuario, compromisso ) );
			}

		}
		else if ( compromisso.getFrequencia() == CompromissoFrenquencia.SEMANALMENTE )
		{

			DateTime finalDateReference = new DateTime( compromisso.getDataFim() );

			DateTime finalDate = finalDateReference.withYear( finalDateReference.getYear() + 1 );

			for ( DateTime dateBegin = new DateTime( compromisso.getDataInicio() ); dateBegin.isBefore( finalDate ); dateBegin = dateBegin.plusWeeks( 1 ) )
			{
				Interval interval = new Interval( new DateTime( compromisso.getDataInicio() ), dateBegin );

				DateTime dateEnd = finalDateReference.plus( interval.toDurationMillis() );

				agendas.add( new Agenda( dateBegin.toDate(), dateEnd.toDate(), usuario, compromisso ) );
			}

		}
		else if ( compromisso.getFrequencia() == CompromissoFrenquencia.MENSALMENTE )
		{

			DateTime finalDateReference = new DateTime( compromisso.getDataFim() );

			DateTime finalDate = finalDateReference.withYear( finalDateReference.getYear() + 2 );

			for ( DateTime dateBegin = new DateTime( compromisso.getDataInicio() ); dateBegin.isBefore( finalDate ); dateBegin = dateBegin.plusMonths( 1 ) )
			{
				Interval interval = new Interval( new DateTime( compromisso.getDataInicio() ), dateBegin );

				DateTime dateEnd = finalDateReference.plus( interval.toDurationMillis() );

				agendas.add( new Agenda( dateBegin.toDate(), dateEnd.toDate(), usuario, compromisso ) );
			}

		}
		else if ( compromisso.getFrequencia() == CompromissoFrenquencia.ANUALMENTE )
		{

			DateTime finalDateReference = new DateTime( compromisso.getDataFim() );

			DateTime finalDate = finalDateReference.withYear( finalDateReference.getYear() + 10 );

			for ( DateTime dateBegin = new DateTime( compromisso.getDataInicio() ); dateBegin.isBefore( finalDate ); dateBegin = dateBegin.plusYears( 1 ) )
			{
				Interval interval = new Interval( new DateTime( compromisso.getDataInicio() ), dateBegin );

				DateTime dateEnd = finalDateReference.plus( interval.toDurationMillis() );

				agendas.add( new Agenda( dateBegin.toDate(), dateEnd.toDate(), usuario, compromisso ) );
			}

		}

		this.agendaRepository.save( agendas );
	}

	/**
	 * 
	 * @param compromisso
	 */
	private void clearAgenda( Compromisso compromisso )
	{
		Assert.notNull( compromisso, "Compromisso não pode ser nulo!" );
		List<Agenda> agendas = this.agendaRepository.listByCompromisso( compromisso.getId() );
		this.agendaRepository.delete( agendas );
	}

	/**
	 * 
	 * @param compromissoId
	 */
	public void removeCompromisso( Long compromissoId )
	{
		User usuario = ContextHolder.getAuthenticatedUser();
		List<Agenda> agendas = this.agendaRepository.listByCompromisso( compromissoId );
		this.agendaRepository.delete( agendas );

		Compromisso compromisso = this.compromissoRepository.findOne( compromissoId );

		this.compromissoRepository.delete( compromisso );

		String log = "Usuário " + usuario.getName() + " excluíu o compromisso " + compromisso.getTitulo();
		EventoLog eventoLog = new EventoLog( log, usuario );
		this.loggingService.insertEventoLog( eventoLog );
		
//		this.loggingService.recordTextFile( usuario.getName(), "Usuário " + usuario.getName() + " excluíu o compromisso " + compromisso.getTitulo() );
	}

	/**
	 * 
	 * @param tipoCompromisso
	 * @return
	 */
	public TipoCompromisso insertTipoCompromisso( String descricao )
	{
		User usuario = ContextHolder.getAuthenticatedUser();

		TipoCompromisso tipoCompromisso = new TipoCompromisso( null, descricao, usuario, false );

		this.loggingService.recordTextFile( usuario.getName(), "Usuário " + usuario.getName() + " cadastrou o tipo de compromisso " + tipoCompromisso.getDescricao() );

		return this.tipoCompromissoRepository.save( tipoCompromisso );
	}

	/**
	 * 
	 * @param tipoCompromisso
	 * @return
	 */
	public TipoCompromisso updateTipoCompromisso( TipoCompromisso tipoCompromisso )
	{
		User usuario = ContextHolder.getAuthenticatedUser();

		tipoCompromisso.setUsuario( usuario );

		String log = "Usuário " + usuario.getName() + " atualizou o tipo de compromisso " + tipoCompromisso.getDescricao();
		EventoLog eventoLog = new EventoLog( log, usuario );
		this.loggingService.insertEventoLog( eventoLog );
//		this.loggingService.recordTextFile( usuario.getName(), "Usuário " + usuario.getName() + " atualizou o tipo de compromisso " + tipoCompromisso.getDescricao() );

		return this.tipoCompromissoRepository.save( tipoCompromisso );
	}

	/**
	 * 
	 * @param categoriaCompromisso
	 * @return
	 */
	public CategoriaCompromisso insertCategoriaCompromisso( String descricao )
	{
		User usuario = ContextHolder.getAuthenticatedUser();

		CategoriaCompromisso categoriaCompromisso = new CategoriaCompromisso( null, descricao, usuario, false );
		
		String log = "Usuário " + usuario.getName() + " cadastrou a categoria de compromisso " + descricao;
		EventoLog eventoLog = new EventoLog( log, usuario );
		this.loggingService.insertEventoLog( eventoLog );
//		this.loggingService.recordTextFile( usuario.getName(), "Usuário " + usuario.getName() + " cadastrou a categoria de compromisso " + descricao );

		return this.categoriaCompromissoRepository.save( categoriaCompromisso );
	}

	/**
	 * 
	 * @param categoriaCompromisso
	 * @return
	 */
	public CategoriaCompromisso updateCategoriaCompromisso( CategoriaCompromisso categoriaCompromisso )
	{
		User usuario = ContextHolder.getAuthenticatedUser();

		categoriaCompromisso.setUsuario( usuario );

		String log = "Usuário " + usuario.getName() + " atualizou a categoria de compromisso " + categoriaCompromisso.getDescricao();
		EventoLog eventoLog = new EventoLog( log, usuario );
		this.loggingService.insertEventoLog( eventoLog );
		
//		this.loggingService.recordTextFile( usuario.getName(), "Usuário " + usuario.getName() + " atualizou a categoria de compromisso " + categoriaCompromisso.getDescricao() );

		return this.categoriaCompromissoRepository.save( categoriaCompromisso );
	}

	/**
	 * 
	 * @return
	 */
	public List<CategoriaCompromisso> listCategoriasCompromissos()
	{
		return this.categoriaCompromissoRepository.listByUser( ContextHolder.getAuthenticatedUser().getId() );
	}

	/**
	 * 
	 * @return
	 */
	public List<TipoCompromisso> listTiposCompromissos()
	{
		return this.tipoCompromissoRepository.listByUser( ContextHolder.getAuthenticatedUser().getId() );
	}

	/**
	 * 
	 * @param categoriaCompromisso
	 */
	public void removeCategoriaCompromisso( CategoriaCompromisso categoriaCompromisso )
	{
		User usuario = ContextHolder.getAuthenticatedUser();

		this.categoriaCompromissoRepository.delete( categoriaCompromisso );

		String log = "Usuário " + usuario.getName() + " excluíu a categoria de compromisso " + categoriaCompromisso.getDescricao();
		EventoLog eventoLog = new EventoLog( log, usuario );
		this.loggingService.insertEventoLog( eventoLog );
		
//		this.loggingService.recordTextFile( usuario.getName(), "Usuário " + usuario.getName() + " excluíu a categoria de compromisso " + categoriaCompromisso.getDescricao() );
	}

	/**
	 * 
	 * @param tipoCompromisso
	 */
	public void removeTipoCompromisso( Long tipoCompromissoId )
	{
		User usuario = ContextHolder.getAuthenticatedUser();

		TipoCompromisso tipoCompromisso = this.tipoCompromissoRepository.findOne( tipoCompromissoId );
		Assert.isTrue( tipoCompromisso.getUsuario().equals( usuario ) );
		this.tipoCompromissoRepository.delete( tipoCompromisso );

		this.loggingService.recordTextFile( usuario.getName(), "Usuário " + usuario.getName() + " excluíu o tipo de compromisso " + tipoCompromisso.getDescricao() );
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public Compromisso findCompromissoById( Long id )
	{
		User usuario = ContextHolder.getAuthenticatedUser();

		Compromisso compromisso = this.compromissoRepository.findOne( id );
		if ( compromisso == null || compromisso.getUsuario().getId() != usuario.getId() )
		{
			CompartilhamentoCompromisso compartilhamentoCompromisso = this.compartilhamentoCompromissoRepository.findSharedByCompromisso( id, usuario.getId() );
			Assert.notNull( compartilhamentoCompromisso );
			compromisso.setShared( true );
		}

		return compromisso;
	}

	/**
	 * 
	 * @param compromisso
	 * @param usuario
	 */
	public CompartilhamentoCompromisso shareCompromissoWithUser( Compromisso compromisso, User usuario )
	{
		Assert.notNull( compromisso, "Compromisso não pode ser nulo!" );
		Assert.notNull( usuario, "Usuário não pode ser nulo!" );

		User usuarioLogado = ContextHolder.getAuthenticatedUser();

		CompartilhamentoCompromisso compartilhamentoCompromisso = new CompartilhamentoCompromisso();
		compartilhamentoCompromisso.setCompromisso( compromisso );
		compartilhamentoCompromisso.setUsuario( usuario );
		compartilhamentoCompromisso.setAutor( usuarioLogado );

		String log = "Usuário " + usuario.getName() + " cadastrou o compromisso " + compromisso.getTitulo();
		EventoLog eventoLog = new EventoLog( log, usuario );
		this.loggingService.insertEventoLog( eventoLog );
		
//		this.loggingService.recordTextFile( usuarioLogado.getName(), "Usuário " + usuarioLogado.getName() + " compartilhou um compromisso com " + usuario.getName() );
		WarningEmailSender emailSender = new WarningEmailSender( compromisso, usuario );
		emailSender.run();

		return this.compartilhamentoCompromissoRepository.save( compartilhamentoCompromisso );
	}

	/**
	 * 
	 * @param compromisso
	 */
	public void removeSharedCompromisso( Compromisso compromisso )
	{
		User usuarioLogado = ContextHolder.getAuthenticatedUser();
		CompartilhamentoCompromisso compartilhamentoCompromisso = this.compartilhamentoCompromissoRepository.findSharedByCompromisso( compromisso.getId(), usuarioLogado.getId() );

		Assert.notNull( compartilhamentoCompromisso, "Nenhum compromisso compartilhado encontrado!" );

		this.compartilhamentoCompromissoRepository.delete( compartilhamentoCompromisso );

	}

	/**
	 * s
	 * 
	 * @param compromisso
	 */
	private void scheduleCompromissoWarningEmail( Compromisso compromisso )
	{
		Timer timer = new Timer();
		// Schedule to run every Sunday in midnight
		Date dataEmail = (Date)compromisso.getDataInicio().clone();
		dataEmail.setHours( dataEmail.getHours() - 1 );
		
		timer.schedule( new WarningEmailSender( compromisso, compromisso.getUsuario(), "O compromisso '"+ compromisso.getTitulo() + "' irá ocorrer daqui a uma hora." ), dataEmail );

	}

}