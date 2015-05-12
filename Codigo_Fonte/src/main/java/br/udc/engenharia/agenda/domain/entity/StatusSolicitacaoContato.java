/**
 * 
 */
package br.udc.engenharia.agenda.domain.entity;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author Henrique
 *
 */
@DataTransferObject(type="enum")
public enum StatusSolicitacaoContato
{

	PENDENTE,
	ACEITA,
	RECUSADA;
	
}
