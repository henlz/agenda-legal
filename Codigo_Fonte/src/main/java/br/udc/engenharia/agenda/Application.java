package br.udc.engenharia.agenda;

import java.util.Properties;

import javax.inject.Inject;

import org.directwebremoting.spring.DwrSpringServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import br.udc.engenharia.agenda.application.security.AuthenticationFailureHandler;
import br.udc.engenharia.agenda.application.security.AuthenticationSuccessHandler;
import br.udc.engenharia.agenda.application.security.LogoutSuccessHandler;

/**
 * 
 * @author rodrigo
 */
@SpringBootApplication
@ImportResource(value = "classpath:/META-INF/spring/agenda-context.xml")
public class Application extends SpringBootServletInitializer
{
	public static void main( String[] args )
	{
		SpringApplication.run( Application.class, args );
	}
	
	@Inject
	private Environment environment;

	@Override
	protected SpringApplicationBuilder configure( SpringApplicationBuilder application )
	{
		return application.sources( Application.class );
	}

//----------WEB
	/**
	 * 
	 * @return
	 */
	@Bean
	public ServletRegistrationBean dwrSpringServletRegistration()
	{
		final ServletRegistrationBean registration = new ServletRegistrationBean( new DwrSpringServlet(), "/broker/*" );
		registration.addInitParameter( "debug", "true" );
		registration.setName( "dwrSpringServlet" );
		return registration;
	}
	
//	@Bean
//	public JavaMailSender createMailSender() {
//		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//		mailSender.setDefaultEncoding("UTF-8");
//		mailSender.setHost("smtp.gmail.com");
//		mailSender.setPort(587);
//		mailSender.setUsername("hnlbzg");
//		mailSender.setPassword("HeloeRick.031012");
//		Properties properties = new Properties();
//		properties.put("mail.smtp.auth", true);
//		properties.put("mail.smtp.starttls.enable", true);
//		mailSender.setJavaMailProperties(properties);
//		return mailSender;
//	}

//----------Security
	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter
	{

		/**
    	 * 
    	 */
		@Override
		protected void configure( HttpSecurity httpSecurity ) throws Exception
		{
			httpSecurity.csrf().disable();

			httpSecurity.authorizeRequests().anyRequest().authenticated().and().formLogin().usernameParameter( "email" ).loginPage( "/authentication" ).loginProcessingUrl( "/authenticate" ).failureHandler( new AuthenticationFailureHandler() ).successHandler( new AuthenticationSuccessHandler() ).permitAll().and().logout().logoutSuccessHandler( new LogoutSuccessHandler() ).logoutUrl( "/logout" );
		}
	}
}