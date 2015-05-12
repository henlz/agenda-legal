package br.udc.engenharia.agenda.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.udc.engenharia.agenda.domain.entity.compromisso.Compromisso;

/**
 * 
 * @author Henrique L. Zago 
 * @since 03/04/2015
 * @version 1.0
 * @category Repository
 */
public interface ICompromissoRepository extends JpaRepository<Compromisso, Long>
{
	/**
	 * @param filter
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new Compromisso( compromisso.id, compromisso.titulo, compromisso.descricao, compromisso.observacoes, compromisso.dataInicio, compromisso.dataFim, compromisso.frequencia, compromisso.status, compromisso.usuario, compromisso.tipoCompromisso, compromisso.categoriaCompromisso ) " +
				   "FROM Compromisso compromisso " +
				  "WHERE  (compromisso.usuario.id = CAST(:userId AS int))" )
	public List<Compromisso> listByFilters( @Param("userId") Long userId );
}
