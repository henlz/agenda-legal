package br.udc.engenharia.agenda.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author rodrigo
 */
@Controller
public class NavigationController
{
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public ModelAndView home()
	{
		return new ModelAndView( "modules/agenda/ui/index.jsp" );
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/authentication")
	public ModelAndView authentication()
	{
		return new ModelAndView( "modules/authentication/ui/index.jsp" );
	}
}
