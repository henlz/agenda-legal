package br.udc.engenharia.agenda.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.udc.engenharia.agenda.domain.entity.compromisso.CompartilhamentoCompromisso;

/**
 * 
 * @author Henrique L. Zago 
 * @since 03/04/2015
 * @version 1.0
 * @category Repository
 */
public interface ICompartilhamentoCompromissoRepository extends JpaRepository<CompartilhamentoCompromisso, Long>
{
	/**
	 * @param filter
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new CompartilhamentoCompromisso( compartilhamentoCompromisso.id, compartilhamentoCompromisso.compromisso, compartilhamentoCompromisso.usuario, compartilhamentoCompromisso.autor ) " +
				   "FROM CompartilhamentoCompromisso compartilhamentoCompromisso " +
				  "WHERE  (compartilhamentoCompromisso.usuario.id = CAST(:userId AS int))" )
	public List<CompartilhamentoCompromisso> listShared( @Param("userId") Long userId );
	
	/**
	 * @param filter
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new CompartilhamentoCompromisso( compartilhamentoCompromisso.id, compartilhamentoCompromisso.compromisso, compartilhamentoCompromisso.usuario, compartilhamentoCompromisso.autor ) " +
			"FROM CompartilhamentoCompromisso compartilhamentoCompromisso " +
			"WHERE  (compartilhamentoCompromisso.usuario.id = CAST(:userId AS int)) "
			+ "AND (compartilhamentoCompromisso.compromisso.id = CAST(:compromissoId AS int))" )
	public CompartilhamentoCompromisso findSharedByCompromisso( @Param("compromissoId") Long compromissoId, @Param("userId") Long userId );
}
