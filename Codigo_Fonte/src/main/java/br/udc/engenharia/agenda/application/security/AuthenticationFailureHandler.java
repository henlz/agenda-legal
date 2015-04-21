package br.udc.engenharia.agenda.application.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;

/**
 * 
 */
public class AuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler
{
	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void onAuthenticationFailure( HttpServletRequest request, HttpServletResponse response, AuthenticationException exception ) throws IOException, ServletException
	{
		// lança excessao caso a senha ou o email estejam errados
		if ( exception instanceof BadCredentialsException )
		{
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
			response.setContentType( "text/plain; charset=iso-8859-1" );
			response.getOutputStream().println( "Credenciais inválidas!" );
		}

		if ( exception instanceof LockedException || exception instanceof DisabledException )
		{
			response.setStatus( HttpServletResponse.SC_FORBIDDEN );
			response.setContentType( "text/plain; charset=iso-8859-1" );
			response.getOutputStream().println( exception.getMessage() );
		}

		// lança excessao caso a senha esteja expirada
		if ( exception instanceof CredentialsExpiredException )
		{
			response.setStatus( HttpServletResponse.SC_NOT_ACCEPTABLE );
			response.setContentType( "text/plain; charset=iso-8859-1" );
			response.getOutputStream().println( exception.getMessage() );
		}
	}
}
