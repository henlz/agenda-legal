/**
 * 
 */
package br.udc.engenharia.agenda.domain.repository;

import java.util.Date;
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
	 * @param compromissoId
	 * @param userId
	 * @return
	 */
	@Query(value="SELECT new Agenda( agenda.id, agenda.dataInicio, agenda.dataFim, agenda.usuario, agenda.compromisso ) "+
			"FROM Agenda agenda " +
			"WHERE ( ( LOWER(agenda.compromisso.titulo) LIKE '%' || LOWER(CAST(:titulo AS string))  || '%' OR :titulo = NULL ) "
			+ "AND (( agenda.dataInicio >= :dataInicio OR CAST( :dataInicio as date ) = NULL ) "
			+ "AND ( agenda.dataFim <= :dataFim OR CAST( :dataFim as date ) = NULL )) "
			+ "AND (agenda.compromisso.tipoCompromisso.id = :tipoCompromissoId OR :tipoCompromissoId = null ) "
			+ "AND (agenda.compromisso.categoriaCompromisso.id = :categoriaCompromissoId OR :tipoCompromissoId = null ) )" )
	public List<Agenda> listByFilters( @Param("titulo") String titulo, @Param("dataInicio") Date dataInicio, @Param("dataFim") Date dataFim, @Param("categoriaCompromissoId") Long categoriaCompromissoId, @Param("tipoCompromissoId") Long tipoCompromissoId );
	
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
