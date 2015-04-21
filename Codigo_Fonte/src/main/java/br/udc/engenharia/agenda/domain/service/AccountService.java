package br.udc.engenharia.agenda.domain.service;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.udc.engenharia.agenda.domain.entity.account.User;
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
	 * @param cliente
	 * @return
	 */
	public User insertUser( User user )
	{
		Assert.notNull( user );

		user.setEnabled( true );
		// encrypt password
		final String encodedPassword = this.passwordEncoder.encodePassword( user.getPassword(), saltSource.getSalt( user ) );
		user.setPassword( encodedPassword );

		return this.userRepository.save( user );
	}
}