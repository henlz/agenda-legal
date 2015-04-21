package br.udc.engenharia.agenda.domain.entity.compromisso;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author Henrique L. Zago
 *
 */
@DataTransferObject(type = "enum", javascript = "CompromissoFrenquencia")
public enum CompromissoFrenquencia
{

	UMA_VEZ, // 0
	DIARIAMENTE, // 1
	SEMANALMENTE, // 2
	MENSALMENTE, // 3
	ANUALMENTE; // 4
	
}
