package br.udc.engenharia.agenda.test;

import java.io.IOException;
import java.util.Timer;

import org.junit.Test;

import br.udc.engenharia.agenda.domain.entity.account.User;
import br.udc.engenharia.agenda.domain.entity.compromisso.Compromisso;
import br.udc.engenharia.agenda.infrastructure.mail.WarningEmailSender;

/**
 * 
 * @author rodrigo
 */
public class MailTests extends AbstractIntegrationTests
{
	/**
	 * 
	 * @throws IOException
	 */
	@Test
	public void TestMail() throws IOException
	{

		Compromisso compromisso = new Compromisso();
		compromisso.setUsuario( new User(null, "Henrique", "lobato.zago@gmail.com") );
		WarningEmailSender warningEmailSender = new WarningEmailSender( compromisso ); 
		// Schedule to run every Sunday in midnight 

		warningEmailSender.run();
	}

}
