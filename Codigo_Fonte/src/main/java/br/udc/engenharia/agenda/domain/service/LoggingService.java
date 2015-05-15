package br.udc.engenharia.agenda.domain.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

/**
 * @author Henrique
 *
 */
@Service
public class LoggingService
{

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
