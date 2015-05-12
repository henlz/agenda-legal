package br.udc.engenharia.agenda.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.udc.engenharia.agenda.domain.entity.account.Contato;

/**
 * @author Henrique
 *
 */
public interface IContatoRepository extends JpaRepository<Contato, Long>
{
}
