package br.udc.engenharia.agenda.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.udc.engenharia.agenda.domain.entity.EventoLog;
import br.udc.engenharia.agenda.domain.entity.StatusSolicitacaoContato;
import br.udc.engenharia.agenda.domain.entity.account.Contato;
import br.udc.engenharia.agenda.domain.entity.account.ContatoUsuario;
import br.udc.engenharia.agenda.domain.entity.account.User;
import br.udc.engenharia.agenda.domain.repository.IContatoRepository;
import br.udc.engenharia.agenda.domain.repository.IContatoUsuarioRepository;
import br.udc.engenharia.agenda.domain.repository.IEventoLogRepository;
import br.udc.engenharia.agenda.domain.repository.account.IUserRepository;

/**
 * 
 * @author rodrigo
 */
@Service
@Transactional
@RemoteProxy(name = "accountService")
public class AccountService
{

	/**
	 * Password encoder
	 */
	@Autowired
	private ShaPasswordEncoder passwordEncoder;

	/**
	 * Hash generator for encryption
	 */
	@Autowired
	private SaltSource saltSource;

	// Repositories
	/**
	 * 
	 */
	@Autowired
	private IUserRepository userRepository;

	/**
	 * 
	 */
	@Autowired
	private IEventoLogRepository eventoLogRepository;

	/**
	 * 
	 */
	@Autowired
	private IContatoRepository contatoRepository;

	/**
	 * 
	 */
	@Autowired
	private IContatoUsuarioRepository contatoUsuarioRepository;

	/**
	 * 
	 */
	@Autowired
	private LoggingService loggingService;

	/**
	 * 
	 * @param cliente
	 * @return
	 */
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public User insertUser( User user )
	{
		Assert.notNull( user );

		user.setEnabled( true );
		// encrypt password
		final String encodedPassword = this.passwordEncoder.encodePassword( user.getPassword(), saltSource.getSalt( user ) );
		user.setPassword( encodedPassword );

		return this.userRepository.save( user );
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public User updateUser( User user )
	{
		System.out.println( "Usuario: " + user.getEnabled() );
		return this.userRepository.save( user );
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public User changeUserStatus( Long id, Boolean status )
	{
		User user = this.userRepository.findOne( id );
		user.setEnabled( status );
		return this.userRepository.save( user );
	}

	/**
	 * 
	 * @param id
	 * @param contatos
	 * @return
	 */
	public User insertContactToUser( Long id, Contato contato )
	{
		User user = this.userRepository.findOne( id );
		user.getContatos().add( contato );
		String log = "Usuário " + user.getName() + " adicionou o contato " + contato.getDescricao();
		EventoLog eventoLog = new EventoLog( log, user );
		this.loggingService.insertEventoLog( eventoLog );
//		this.loggingService.recordTextFile( user.getName(), "Usuário " + user.getName() + " adicionou o contato " + contato.getDescricao() );
		return this.userRepository.save( user );
	}

	/**
	 * 
	 * @param id
	 * @param contatos
	 * @return
	 */
	public User updateContactToUser( Long id, Contato contato )
	{
		this.contatoRepository.save( contato );

		User user = this.userRepository.findOne( id );
		return this.userRepository.save( user );
	}

	/**
	 * 
	 */
	public void removeContactFromUser( Long id, Contato contato )
	{
		User user = this.userRepository.findOne( id );
		user.getContatos().remove( contato );
		this.userRepository.save( user );
		this.contatoRepository.delete( contato );
		String log = "Usuário " + user.getName() + " removeu o contato " + contato.getDescricao();
		EventoLog eventoLog = new EventoLog( log, user );
		this.loggingService.insertEventoLog( eventoLog );
//		this.loggingService.recordTextFile( user.getName(), "Usuário " + user.getName() + " adicionou o contato " + contato.getDescricao() );
	}

	/**
	 * 
	 * @param filters
	 * @return
	 */
	private User findUser( String filters )
	{
		User user = this.userRepository.findByName( filters );

		if ( user != null )
		{
			return user;
		}

		user = this.userRepository.findByEmail( filters );

		if ( user != null )
		{
			return user;
		}

		return null;
	}

	/**
	 * 
	 * @param filters
	 * @return
	 */
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public User findUserById( Long id )
	{
		return this.userRepository.findOne( id );
	}

	/**
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	public List<User> listUsuarios()
	{
		return this.userRepository.findAll();
	}

	/**
	 * 
	 * @param userInput
	 * @return
	 * @throws Exception
	 */
	public ContatoUsuario sendFriendRequest( String userInput ) throws Exception
	{
		User user = this.findUser( userInput );

		if ( user != null )
		{
			User usuarioLogado = ( User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			ContatoUsuario contatoUsuario = new ContatoUsuario( null, StatusSolicitacaoContato.PENDENTE, usuarioLogado, user );
			String log = "Usuário " + usuarioLogado.getName() + " enviou uma solicitação de contato para " + user.getName();
			EventoLog eventoLog = new EventoLog( log, user );
			this.loggingService.insertEventoLog( eventoLog );
			
//			this.loggingService.recordTextFile( usuarioLogado.getName(), "Usuário " + usuarioLogado.getName() + " enviou uma solicitação de contato para " + user.getName() );
			
			return this.contatoUsuarioRepository.save( contatoUsuario );
		}
		else
		{
			throw new Exception( "Usuario não encontrado!" );
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public ContatoUsuario acceptFriendRequest( Long id )
	{
		Assert.notNull( id );
		ContatoUsuario contatoUsuario = this.contatoUsuarioRepository.findOne( id );
		contatoUsuario.setStatus( StatusSolicitacaoContato.ACEITA );
		
		String log = "Usuário " + contatoUsuario.getUsuarioDestino().getName() + " aceitou a solicitação de contato do usuário " + contatoUsuario.getUsuarioOrigem().getName();
		EventoLog eventoLog = new EventoLog( log, contatoUsuario.getUsuarioDestino() );
		this.loggingService.insertEventoLog( eventoLog );
		
//		this.loggingService.recordTextFile( contatoUsuario.getUsuarioDestino().getName(), "Usuário " + contatoUsuario.getUsuarioDestino().getName() + " aceitou a solicitação de contato do usuário " + contatoUsuario.getUsuarioOrigem().getName() );
		
		return this.contatoUsuarioRepository.save( contatoUsuario );
	}

	/**
	 * 
	 * @param userContactId
	 */
	public void removeFriend( Long userContactId )
	{
		this.contatoUsuarioRepository.delete( userContactId );
	}

	/**
	 * 
	 * @param usuario
	 * @return
	 */
	public List<ContatoUsuario> getFriends()
	{
		User usuarioLogado = ( User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return this.contatoUsuarioRepository.listActiveByUser( usuarioLogado.getId() );
	}

	/**
	 * 
	 * @return
	 */
	public User getCurrentUser()
	{
		User usuario = ( User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		usuario = this.userRepository.findOne( usuario.getId() );
		return usuario;
	}

	/**
	 * 
	 * @return
	 */
	public List<User> getFriendsList()
	{
		User usuarioLogado = ( User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<ContatoUsuario> contatos = this.contatoUsuarioRepository.listByUser( usuarioLogado.getId(), StatusSolicitacaoContato.ACEITA );

		List<User> friends = new ArrayList<User>();

		for ( ContatoUsuario contato : contatos )
		{
			if ( usuarioLogado.getId().equals( contato.getUsuarioDestino().getId() ) )
			{
				friends.add( contato.getUsuarioOrigem() );
			}
			else
			{
				friends.add( contato.getUsuarioDestino() );
			}
		}

		return friends;
	}

	/**
	 * 
	 * @return
	 */
	public List<EventoLog> listEventosLog()
	{
		User usuarioLogado = ( User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return this.eventoLogRepository.findByUsuario( usuarioLogado );
	}

	public EventoLog insertEventoLog( EventoLog eventoLog )
	{
		return this.eventoLogRepository.save( eventoLog );
	}
}