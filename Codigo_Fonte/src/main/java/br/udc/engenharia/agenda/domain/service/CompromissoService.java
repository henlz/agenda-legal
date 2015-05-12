package br.udc.engenharia.agenda.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.udc.engenharia.agenda.domain.entity.account.User;
import br.udc.engenharia.agenda.domain.entity.compromisso.Agenda;
import br.udc.engenharia.agenda.domain.entity.compromisso.CategoriaCompromisso;
import br.udc.engenharia.agenda.domain.entity.compromisso.Compromisso;
import br.udc.engenharia.agenda.domain.entity.compromisso.CompromissoFrenquencia;
import br.udc.engenharia.agenda.domain.entity.compromisso.TipoCompromisso;
import br.udc.engenharia.agenda.domain.repository.IAgendaRepository;
import br.udc.engenharia.agenda.domain.repository.ICategoriaCompromissoRepository;
import br.udc.engenharia.agenda.domain.repository.ICompromissoRepository;
import br.udc.engenharia.agenda.domain.repository.ITipoCompromissoRepository;

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
	 * @param compromisso
	 * @return
	 */
	public Compromisso insertCompromisso( Compromisso compromisso )
	{
		Assert.notNull( compromisso );

		User usuario = ( User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		compromisso.setUsuario( usuario );

		this.arrangeAgenda( compromisso );

		return this.compromissoRepository.save( compromisso );
	}

	/**
	 * 
	 * @param compromisso
	 * @return
	 */
	public Compromisso updateCompromisso( Compromisso compromisso )
	{
		Assert.notNull( compromisso );

		User usuario = ( User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		compromisso.setUsuario( usuario );

		this.clearAgenda( compromisso );
		this.arrangeAgenda( compromisso );

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
		Long userId = ( ( User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getId();

		List<Compromisso> compromissos = this.compromissoRepository.listByFilters( userId );

		for ( Compromisso compromisso : compromissos )
		{
			final List<Agenda> agendas = this.agendaRepository.listByCompromisso( compromisso.getId(), userId );

			compromisso.setAgendas( agendas );
		}

		return compromissos;
	}

	/**
	 * 
	 * @param compromisso
	 */
	private void arrangeAgenda( Compromisso compromisso )
	{
		User usuario = ( User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

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
		User usuario = ( User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<Agenda> agendas = this.agendaRepository.listByCompromisso( compromisso.getId(), usuario.getId() );

		this.agendaRepository.delete( agendas );
	}

	/**
	 * 
	 * @param compromissoId
	 */
	public void removeCompromisso( Long compromissoId )
	{
		List<Agenda> agendas = this.agendaRepository.listByCompromisso( compromissoId, ( ( User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getId() );
		this.agendaRepository.delete( agendas );

		this.compromissoRepository.delete( compromissoId );
	}

	/**
	 * 
	 * @param tipoCompromisso
	 * @return
	 */
	public TipoCompromisso insertTipoCompromisso( String descricao )
	{
		TipoCompromisso tipoCompromisso = new TipoCompromisso( null, descricao, ( User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal() );

		return this.tipoCompromissoRepository.save( tipoCompromisso );
	}

	/**
	 * 
	 * @param tipoCompromisso
	 * @return
	 */
	public TipoCompromisso updateTipoCompromisso( TipoCompromisso tipoCompromisso )
	{
		tipoCompromisso.setUsuario( ( User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal() );

		return this.tipoCompromissoRepository.save( tipoCompromisso );
	}

	/**
	 * 
	 * @param categoriaCompromisso
	 * @return
	 */
	public CategoriaCompromisso insertCategoriaCompromisso( String descricao )
	{
		CategoriaCompromisso categoriaCompromisso = new CategoriaCompromisso( null, descricao, ( User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal() );

		return this.categoriaCompromissoRepository.save( categoriaCompromisso );
	}

	/**
	 * 
	 * @param categoriaCompromisso
	 * @return
	 */
	public CategoriaCompromisso updateCategoriaCompromisso( CategoriaCompromisso categoriaCompromisso )
	{
		categoriaCompromisso.setUsuario( ( User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal() );

		return this.categoriaCompromissoRepository.save( categoriaCompromisso );
	}

	/**
	 * 
	 * @return
	 */
	public List<CategoriaCompromisso> listCategoriasCompromissos()
	{
		return this.categoriaCompromissoRepository.listByUser( ( ( User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getId() );
	}

	/**
	 * 
	 * @return
	 */
	public List<TipoCompromisso> listTiposCompromissos()
	{
		return this.tipoCompromissoRepository.listByUser( ( ( User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getId() );
	}

	/**
	 * 
	 * @param categoriaCompromisso
	 */
	public void removeCategoriaCompromisso( CategoriaCompromisso categoriaCompromisso )
	{
		this.categoriaCompromissoRepository.delete( categoriaCompromisso );
	}

	/**
	 * 
	 * @param tipoCompromisso
	 */
	public void removeTipoCompromisso( Long tipoCompromissoId )
	{
		this.tipoCompromissoRepository.delete( tipoCompromissoId );
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Compromisso findCompromissoById( Long id )
	{
		return this.compromissoRepository.findOne( id );
	}

}