/**
 * 
 */
package br.udc.engenharia.agenda.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.validator.constraints.NotEmpty;

import br.udc.engenharia.agenda.domain.entity.account.User;

/**
 * @author Henrique
 *
 */
@Entity
@DataTransferObject
public class EventoLog extends AbstractEntity
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6510329731901901940L;

	/**
	 * 
	 */
	@NotEmpty
	@Column
	private String log;

	/**
	 * 
	 */
	@ManyToOne
	private User usuario;

	public EventoLog()
	{

	}

	/**
	 * @param log
	 * @param usuario
	 */
	public EventoLog( String log, User usuario )
	{
		super();
		this.log = log;
		this.usuario = usuario;
	}

	/**
	 * @return the log
	 */
	public String getLog()
	{
		return log;
	}

	/**
	 * @param log the log to set
	 */
	public void setLog( String log )
	{
		this.log = log;
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

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( log == null ) ? 0 : log.hashCode() );
		result = prime * result + ( ( usuario == null ) ? 0 : usuario.hashCode() );
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		if ( this == obj ) return true;
		if ( !super.equals( obj ) ) return false;
		if ( getClass() != obj.getClass() ) return false;
		EventoLog other = ( EventoLog ) obj;
		if ( log == null )
		{
			if ( other.log != null ) return false;
		}
		else if ( !log.equals( other.log ) ) return false;
		if ( usuario == null )
		{
			if ( other.usuario != null ) return false;
		}
		else if ( !usuario.equals( other.usuario ) ) return false;
		return true;
	}

}
