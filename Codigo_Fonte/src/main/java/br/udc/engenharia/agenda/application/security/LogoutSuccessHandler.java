package br.udc.engenharia.agenda.application.security;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.security.core.Authentication;

import br.udc.engenharia.agenda.domain.entity.account.User;

/**
 * @author Henrique
 *
 */
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler
{

	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.web.authentication.logout.LogoutSuccessHandler#onLogoutSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	@Override
	public void onLogoutSuccess( HttpServletRequest request, HttpServletResponse response, Authentication authentication ) throws IOException, ServletException
	{

		User usuario = ( User ) authentication.getPrincipal();

		try
		{

			File file = new File( "C:/Agenda Legal Logs/" + usuario.getName() + ".log" );

			if ( !file.exists() )
			{
				file.createNewFile();
			}

			String content = DateTime.now() + " | " + "Usuário " + usuario.getName() + " saiu do sistema";

			FileWriter fw = new FileWriter( file.getAbsoluteFile(), true );
			BufferedWriter bw = new BufferedWriter( fw );
			bw.append( content + System.getProperty( "line.separator" ) );
			bw.close();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}

		response.sendRedirect("authentication");
	}

}
