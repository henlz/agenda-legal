/**
 * 
 */
package br.udc.engenharia.agenda.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.udc.engenharia.agenda.domain.entity.compromisso.Agenda;

/**
 * @author Henrique
 *
 */
public interface IAgendaRepository extends JpaRepository<Agenda, Long>
{

	/**
	 * 
	 * @param compromissoId
	 * @param userId
	 * @return
	 */
	@Query(value="SELECT new Agenda( agenda.id, agenda.dataInicio, agenda.dataFim, agenda.usuario, agenda.compromisso ) " +
			   "FROM Agenda agenda " +
			  "WHERE  ( agenda.compromisso.id = :compromissoId ) " )
	public List<Agenda> listByCompromisso( @Param("compromissoId") Long compromissoId);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	@Query(value="SELECT new Agenda( agenda.dataInicio, agenda.dataFim, agenda.usuario, agenda.compromisso ) " +
			"FROM Agenda agenda " +
			"WHERE  ( agenda.usuario.id = :userId )" )
	public List<Agenda> listByUser( @Param("userId") Long userId);
	
}
