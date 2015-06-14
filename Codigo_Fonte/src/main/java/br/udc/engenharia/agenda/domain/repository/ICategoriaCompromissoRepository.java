package br.udc.engenharia.agenda.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.udc.engenharia.agenda.domain.entity.compromisso.CategoriaCompromisso;

/**
 * 
 * @author Henrique L. Zago 
 * @since 03/04/2015
 * @version 1.0
 * @category Repository
 */
public interface ICategoriaCompromissoRepository extends JpaRepository<CategoriaCompromisso, Long>
{
	/**
	 * @param filter
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new CategoriaCompromisso( categoriaCompromisso.id, categoriaCompromisso.descricao, categoriaCompromisso.usuario, categoriaCompromisso.doSistema ) " +
				   "FROM CategoriaCompromisso categoriaCompromisso " +
				   "WHERE ( categoriaCompromisso.doSistema = TRUE "
				  + "OR categoriaCompromisso.usuario.id = CAST(:userId AS int) "
				  + "OR :userId = NULL)" )
	public List<CategoriaCompromisso> listByUser( @Param("userId") Long userId );
}
