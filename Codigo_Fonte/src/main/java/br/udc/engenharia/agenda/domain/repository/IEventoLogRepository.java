/**
 * 
 */
package br.udc.engenharia.agenda.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.udc.engenharia.agenda.domain.entity.EventoLog;
import br.udc.engenharia.agenda.domain.entity.account.User;

/**
 * @author Henrique
 *
 */
public interface IEventoLogRepository extends JpaRepository<EventoLog, Long>
{

	/**
	 * 
	 * @return
	 */
	public List<EventoLog> findByUsuario( User usuario );

}
