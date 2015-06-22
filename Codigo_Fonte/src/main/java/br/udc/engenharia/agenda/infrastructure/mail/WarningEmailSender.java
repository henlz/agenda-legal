package br.udc.engenharia.agenda.infrastructure.mail;

import java.util.Properties;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import br.udc.engenharia.agenda.domain.entity.account.User;
import br.udc.engenharia.agenda.domain.entity.compromisso.Compromisso;

/**
 * @author Henrique
 *
 */
public class WarningEmailSender extends TimerTask
{

	/**
	 * 
	 */
	private Compromisso compromisso;
	
	/**
	 * 
	 */
	private User destinatario;
	
	/**
	 * 
	 */
	private String text;

	/**
	 * 
	 */
	private MailSender mailSender;

	/**
	 * 
	 */
	public WarningEmailSender()
	{

	}

	/**
	 * 
	 * @param compromisso
	 */
	public WarningEmailSender( Compromisso compromisso )
	{
		super();
		this.compromisso = compromisso;
		this.mailSender = this.initMailSender();
	}
	
	public WarningEmailSender( Compromisso compromisso, User destinatario )
	{
		super();
		this.compromisso = compromisso;
		this.destinatario = destinatario;
		this.mailSender = this.initMailSender();
	}
	
	public WarningEmailSender( Compromisso compromisso, User destinatario, String text )
	{
		super();
		this.compromisso = compromisso;
		this.destinatario = destinatario;
		this.text = text;
		this.mailSender = this.initMailSender();
	}
	
	/**
	 * 
	 * @return
	 */
	private MailSender initMailSender()
	{
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setDefaultEncoding("UTF-8");
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("email");
		mailSender.setPassword("senha");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", true);
		mailSender.setJavaMailProperties(properties);
		return mailSender;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	@Override
	@PostConstruct
	public void run()
	{
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setReplyTo( compromisso.getUsuario().getEmail() );
		msg.setFrom( compromisso.getUsuario().getEmail() );
		msg.setSubject( "Aviso de compromisso" );
		msg.setTo( this.destinatario.getEmail() );
		if (text != null) {
			msg.setText( text );
		} else {
			msg.setText( "O usuário " + compromisso.getUsuario().getName() + ", compartilhou o compromisso '" + this.compromisso.getTitulo() + "' com você." );
		}
		System.out.println( this.mailSender );
		this.mailSender.send( msg );
	}
	
	/**
	 * @return the compromisso
	 */
	public Compromisso getCompromisso()
	{
		return compromisso;
	}

	/**
	 * @param compromisso the compromisso to set
	 */
	public void setCompromisso( Compromisso compromisso )
	{
		this.compromisso = compromisso;
	}

}
