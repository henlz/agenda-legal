package br.udc.engenharia.agenda.test.service;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.udc.engenharia.agenda.domain.entity.account.User;
import br.udc.engenharia.agenda.domain.entity.account.UserRole;
import br.udc.engenharia.agenda.domain.service.AccountService;
import br.udc.engenharia.agenda.test.AbstractIntegrationTests;

/**
 * 
 * @author rodrigo
 */
public class AccountServiceTests extends AbstractIntegrationTests
{
	/**
	 * 
	 */
	@Autowired
	private AccountService accountService;

	/**
	 * 
	 * @throws IOException
	 */
	@Test
	public void inserUser() throws IOException
	{
		User user = new User( 1L, "Administrador de Sistemas", "admin@agendatop.com.br", true, UserRole.ADMINISTRATOR, "admin" );
		this.accountService.insertUser( user );
	}
}
