package br.udc.engenharia.agenda.test.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.udc.engenharia.agenda.domain.service.CompromissoService;
import br.udc.engenharia.agenda.test.AbstractIntegrationTests;

/**
 * 
 * @author rodrigo
 */
public class CompromissoServiceTests extends AbstractIntegrationTests
{
	/**
	 * 
	 */
	@Autowired
	private CompromissoService compromissoService;

	/**
	 * 
	 * @throws IOException
	 */
//	@Test
//	public void inserCompromisso()
//	{
//		Compromisso compromisso = new Compromisso("Titulo", "Descrição", "Observações", new GregorianCalendar(2015, 11, 12), new GregorianCalendar(2015, 11, 25), CompromissoFrenquencia.DIARIAMENTE, Status.DISPONIVEL, (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal() );
//		
//		this.compromissoService.insertCompromisso( compromisso );
//	}
}
