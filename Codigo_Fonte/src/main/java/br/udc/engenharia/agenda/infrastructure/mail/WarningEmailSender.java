/**
 * 
 */
package br.udc.engenharia.agenda.infrastructure.mail;

import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

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
	@Autowired
	private MailSender mailSender;
	
	public WarningEmailSender(Compromisso compromisso)
	{
		super();
		this.compromisso = compromisso;
	}

	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run()
	{
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setSubject( "" );
        msg.setTo(compromisso.getUsuario().getEmail());
        msg.setText(
            "Caro " + compromisso.getUsuario().getName()
                + ", thank you for placing order. Your order number is ");
        try{
            this.mailSender.send(msg);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
	}

}
