/**
 * 
 */
package br.udc.engenharia.agenda.infrastructure.mail;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author Henrique
 *
 */
public class MailMail
{
	private MailSender mailSender;

	public void setMailSender( MailSender mailSender )
	{
		this.mailSender = mailSender;
	}

	public void sendMail( SimpleMailMessage message )
	{
		mailSender.send( message );
	}
}
