package br.udc.engenharia.agenda.application.security;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import br.udc.engenharia.agenda.domain.entity.account.User;
import br.udc.engenharia.agenda.domain.service.LoggingService;

/**
 * @author Henrique
 *
 */
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler
{

	/**
	 * 
	 */
	@Autowired
	private LoggingService loggindService;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	@Override
	public void onAuthenticationSuccess( HttpServletRequest request, HttpServletResponse response, Authentication authentication ) throws IOException, ServletException
	{
		User usuario = ( User ) authentication.getPrincipal();
		
		try {
			 
			File file = new File("C:/Agenda Legal Logs/"+usuario.getName()+".log");
 
			if (!file.exists()) {
				file.createNewFile();
			}
 
			String content = DateTime.now() + " | " + "Usuário " + usuario.getName() + " se autenticou no sistema";
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.append(content + System.getProperty("line.separator"));
			bw.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
