package br.udc.engenharia.agenda.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.udc.engenharia.agenda.domain.entity.compromisso.TipoCompromisso;

/**
 * 
 * @author Henrique L. Zago 
 * @since 03/04/2015
 * @version 1.0
 * @category Repository
 */
public interface ITipoCompromissoRepository extends JpaRepository<TipoCompromisso, Long>
{
	/**
	 * @param filter
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new TipoCompromisso( tipoCompromisso.id, tipoCompromisso.descricao, tipoCompromisso.usuario ) " +
				   "FROM TipoCompromisso tipoCompromisso " +
				  "WHERE (tipoCompromisso.usuario.id = CAST(:userId AS int) OR :userId = NULL)" )
	public List<TipoCompromisso> listByUser( @Param("userId") Long userId );
}
