package br.udc.engenharia.agenda.domain.entity.compromisso;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author Henrique L. Zago
 *
 */
@DataTransferObject(type="enum")
public enum Status
{
	DISPONIVEL, // 0
	OCUPADO; // 1
}
