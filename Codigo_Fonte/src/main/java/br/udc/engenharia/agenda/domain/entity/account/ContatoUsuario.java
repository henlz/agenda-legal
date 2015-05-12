/**
 * 
 */
package br.udc.engenharia.agenda.domain.entity.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;

import br.udc.engenharia.agenda.domain.entity.AbstractEntity;
import br.udc.engenharia.agenda.domain.entity.StatusSolicitacaoContato;

/**
 * @author Henrique
 *
 */
@Entity
@DataTransferObject
public class ContatoUsuario extends AbstractEntity
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1130142765028466898L;

	/**
	 * 
	 */
	@Column
	private StatusSolicitacaoContato status;

	/**
	 * 
	 */
	@NotNull
	@ManyToOne
	private User usuarioOrigem;

	/**
	 * 
	 */
	@NotNull
	@ManyToOne
	private User usuarioDestino;

	/**
	 * 
	 */
	public ContatoUsuario()
	{
	}
	
	/**
	 * @param status
	 * @param usuarioOrigem
	 * @param usuarioDestino
	 */
	public ContatoUsuario( Long id, StatusSolicitacaoContato status, User usuarioOrigem, User usuarioDestino )
	{
		super( id );
		this.status = status;
		this.usuarioOrigem = usuarioOrigem;
		this.usuarioDestino = usuarioDestino;
	}

	/**
	 * @return the status
	 */
	public StatusSolicitacaoContato getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus( StatusSolicitacaoContato status )
	{
		this.status = status;
	}

	/**
	 * @return the usuarioOrigem
	 */
	public User getUsuarioOrigem()
	{
		return usuarioOrigem;
	}

	/**
	 * @param usuarioOrigem the usuarioOrigem to set
	 */
	public void setUsuarioOrigem( User usuarioOrigem )
	{
		this.usuarioOrigem = usuarioOrigem;
	}

	/**
	 * @return the usuarioDestino
	 */
	public User getUsuarioDestino()
	{
		return usuarioDestino;
	}

	/**
	 * @param usuarioDestino the usuarioDestino to set
	 */
	public void setUsuarioDestino( User usuarioDestino )
	{
		this.usuarioDestino = usuarioDestino;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( status == null ) ? 0 : status.hashCode() );
		result = prime * result + ( ( usuarioDestino == null ) ? 0 : usuarioDestino.hashCode() );
		result = prime * result + ( ( usuarioOrigem == null ) ? 0 : usuarioOrigem.hashCode() );
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
		ContatoUsuario other = ( ContatoUsuario ) obj;
		if ( status != other.status ) return false;
		if ( usuarioDestino == null )
		{
			if ( other.usuarioDestino != null ) return false;
		}
		else if ( !usuarioDestino.equals( other.usuarioDestino ) ) return false;
		if ( usuarioOrigem == null )
		{
			if ( other.usuarioOrigem != null ) return false;
		}
		else if ( !usuarioOrigem.equals( other.usuarioOrigem ) ) return false;
		return true;
	}

	
	
}
