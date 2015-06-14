package br.udc.engenharia.agenda.domain.entity.compromisso;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.Param;

import br.udc.engenharia.agenda.domain.entity.AbstractEntity;
import br.udc.engenharia.agenda.domain.entity.account.User;

/**
 * @author Henrique L. Zago
 * @since 07/04/2015
 *
 */
@Entity
@DataTransferObject(javascript = "CategoriaCompromisso", params=@Param(name="exclude", value="usuario"))
public class CategoriaCompromisso extends AbstractEntity implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 667764268697405824L;

	/**
	 * 
	 */
	@NotNull
	@Column
	private String descricao;
	
	
	/**
	 * 
	 */
	@ManyToOne
	private User usuario;
	
	/**
	 * 
	 */
	@Column
	private Boolean doSistema;

	/**
	 * 
	 */
	public CategoriaCompromisso()
	{
		super();
	}
	
	/**
	 * @param descricao
	 * @param usuario
	 */
	public CategoriaCompromisso( Long id, String descricao, User usuario, Boolean doSistema )
	{
		super(id);
		this.descricao = descricao;
		this.usuario = usuario;
		this.doSistema = doSistema;
	}
	
	/**
	 * @param descricao
	 * @param usuario
	 */
	public CategoriaCompromisso( String descricao, User usuario )
	{
		super();
		this.descricao = descricao;
		this.usuario = usuario;
	}


	/**
	 * @return the descricao
	 */
	public String getDescricao()
	{
		return descricao;
	}


	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao( String descricao )
	{
		this.descricao = descricao;
	}


	/**
	 * @return the usuario
	 */
	public User getUsuario()
	{
		return usuario;
	}


	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario( User usuario )
	{
		this.usuario = usuario;
	}

	/**
	 * @return the doSistema
	 */
	public Boolean getDoSistema()
	{
		return doSistema;
	}

	/**
	 * @param doSistema the doSistema to set
	 */
	public void setDoSistema( Boolean doSistema )
	{
		this.doSistema = doSistema;
	}
	
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
	
	
}
