package br.udc.engenharia.agenda.domain.entity.compromisso;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;

import br.udc.engenharia.agenda.domain.entity.AbstractEntity;
import br.udc.engenharia.agenda.domain.entity.account.User;

/**
 * @author Henrique L. Zago
 *
 */
@Entity
@DataTransferObject(javascript = "Agenda")
public class Agenda extends AbstractEntity implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4987346734165976315L;

	/**
	 * 
	 */
	@NotNull
	@Column
	private Date dataInicio;
	
	/**
	 * 
	 */
	@NotNull
	@Column
	private Date dataFim;
	
	/**
	 * 
	 */
	@ManyToOne
	private User usuario;
	
	/**
	 * 
	 */
	@ManyToOne
	private Compromisso compromisso;

	
	public Agenda()
	{
		super();
	}
	
	/**
	 * @param dataInicio
	 * @param dataFim
	 * @param usuario
	 * @param compromisso
	 */
	public Agenda( Long id, Date dataInicio, Date dataFim, User usuario, Compromisso compromisso )
	{
		super( id );
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.usuario = usuario;
		this.compromisso = compromisso;
	}
	
	/**
	 * @param dataInicio
	 * @param dataFim
	 * @param usuario
	 * @param compromisso
	 */
	public Agenda( Date dataInicio, Date dataFim, User usuario, Compromisso compromisso )
	{
		super( );
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.usuario = usuario;
		this.compromisso = compromisso;
	}

	/**
	 * @return the dataInicio
	 */
	public Date getDataInicio()
	{
		return dataInicio;
	}

	/**
	 * @param dataInicio the dataInicio to set
	 */
	public void setDataInicio( Date dataInicio )
	{
		this.dataInicio = dataInicio;
	}

	/**
	 * @return the dataFim
	 */
	public Date getDataFim()
	{
		return dataFim;
	}

	/**
	 * @param dataFim the dataFim to set
	 */
	public void setDataFim( Date dataFim )
	{
		this.dataFim = dataFim;
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
	 * @return the compromisso
	 */
	public Compromisso getCompromisso()
	{
		return compromisso;
	}

	/**
	 * @param compromisso the compromisso to set
	 */
	public void setCompromisso( Compromisso compromisso )
	{
		this.compromisso = compromisso;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( compromisso == null ) ? 0 : compromisso.hashCode() );
		result = prime * result + ( ( dataFim == null ) ? 0 : dataFim.hashCode() );
		result = prime * result + ( ( dataInicio == null ) ? 0 : dataInicio.hashCode() );
		result = prime * result + ( ( usuario == null ) ? 0 : usuario.hashCode() );
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		if ( this == obj ) return true;
		if ( !super.equals( obj ) ) return false;
		if ( getClass() != obj.getClass() ) return false;
		Agenda other = ( Agenda ) obj;
		if ( compromisso == null )
		{
			if ( other.compromisso != null ) return false;
		}
		else if ( !compromisso.equals( other.compromisso ) ) return false;
		if ( dataFim == null )
		{
			if ( other.dataFim != null ) return false;
		}
		else if ( !dataFim.equals( other.dataFim ) ) return false;
		if ( dataInicio == null )
		{
			if ( other.dataInicio != null ) return false;
		}
		else if ( !dataInicio.equals( other.dataInicio ) ) return false;
		if ( usuario == null )
		{
			if ( other.usuario != null ) return false;
		}
		else if ( !usuario.equals( other.usuario ) ) return false;
		return true;
	}

	
	
}
