package br.udc.engenharia.agenda.domain.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.udc.engenharia.agenda.domain.entity.EventoLog;
import br.udc.engenharia.agenda.domain.entity.account.User;
import br.udc.engenharia.agenda.domain.repository.IEventoLogRepository;

/**
 * @author Henrique
 *
 */
@Service
public class LoggingService
{

	/**
	 * 
	 */
	@Autowired
	private IEventoLogRepository eventoLogRepository;
	
	/**
	 * 
	 * @param fileName
	 * @param text
	 */
//	public void recordLogTeste( String fileName, String text )
//	{
//		
//		Logger logger = Logger.getLogger( fileName );
//		logger.setUseParentHandlers(false);
//		FileHandler fh;
//
//		try
//		{
//			// This block configure the logger with handler and formatter
//			fh = new FileHandler( fileName + ".log" );
//			logger.addHandler( fh );
//			SimpleFormatter formatter = new SimpleFormatter();
//			fh.setFormatter( formatter );
//
//			// the following statement is used to log any messages
//			logger.info( text );
//		}
//		catch ( SecurityException e )
//		{
//			e.printStackTrace();
//		}
//		catch ( IOException e )
//		{
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 
	 * @return
	 */
	public List<EventoLog> listEventosLog()
	{
		User usuarioLogado = ( User ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return this.eventoLogRepository.findByUsuario( usuarioLogado );
	}

	/**
	 * 
	 * @param eventoLog
	 * @return
	 */
	public EventoLog insertEventoLog( EventoLog eventoLog )
	{
		return this.eventoLogRepository.save( eventoLog );
	}
	
	/**
	 * 
	 * @param fileName
	 * @param text
	 */
	public void recordTextFile (String fileName, String text)
	{
		try {
			 
			File file = new File(fileName+".log");
 
			if (!file.exists()) {
				file.createNewFile();
			}
 
			String content = DateTime.now() + " | " + text;
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.append(content + System.getProperty("line.separator"));
			bw.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
