package br.udc.engenharia.agenda.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.udc.engenharia.agenda.domain.entity.StatusSolicitacaoContato;
import br.udc.engenharia.agenda.domain.entity.account.ContatoUsuario;

/**
 * 
 * @author Henrique L. Zago 
 * @since 03/04/2015
 * @version 1.0
 * @category Repository
 */
public interface IContatoUsuarioRepository extends JpaRepository<ContatoUsuario, Long>
{
	/**
	 * @param filter
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new ContatoUsuario( contatoUsuario.id, contatoUsuario.status, contatoUsuario.usuarioOrigem, contatoUsuario.usuarioDestino ) " +
				   "FROM ContatoUsuario contatoUsuario " +
				  "WHERE (contatoUsuario.usuarioOrigem.id = CAST(:userId AS int) OR :userId = NULL) "
				  + "OR (contatoUsuario.status = :statusSolicitacao)" )
	public List<ContatoUsuario> listByUser( @Param("userId") Long userId, @Param("statusSolicitacao") StatusSolicitacaoContato status );
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	@Query(value="SELECT new ContatoUsuario( contatoUsuario.id, contatoUsuario.status, contatoUsuario.usuarioOrigem, contatoUsuario.usuarioDestino ) " +
			   "FROM ContatoUsuario contatoUsuario " +
			  "WHERE ((contatoUsuario.usuarioOrigem.id = CAST(:userId AS int) OR :userId = NULL) "
			  + "OR (contatoUsuario.usuarioDestino.id = CAST(:userId AS int) OR :userId = NULL)) "
			  + "AND (contatoUsuario.status != 2) " )
	public List<ContatoUsuario> listActiveByUser( @Param("userId") Long userId );
	
}
