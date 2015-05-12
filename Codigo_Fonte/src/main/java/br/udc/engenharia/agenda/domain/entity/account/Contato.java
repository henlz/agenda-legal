package br.udc.engenharia.agenda.domain.entity.account;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.directwebremoting.annotations.DataTransferObject;

import br.udc.engenharia.agenda.domain.entity.AbstractEntity;

/**
 * @author Henrique
 *
 */
@Entity
@DataTransferObject(javascript="Contato")
public class Contato extends AbstractEntity
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3477739088682603600L;

	/**
	 * 
	 */
	@Column
	private String descricao;

	/**
	 * 
	 */
	@Column
	private String telefone;

	/**
	 * 
	 */
	@Column
	private String email;

	/**
	 * 
	 */
	public Contato()
	{
	}

	/**
	 * 
	 * @param id
	 */
	public Contato( Long id )
	{
		super( id );
	}

	/**
	 * 
	 * @param id
	 * @param descricao
	 * @param telefone
	 * @param email
	 */
	public Contato( Long id, String descricao, String telefone, String email )
	{
		super(id);
		this.descricao = descricao;
		this.telefone = telefone;
		this.email = email;
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
	 * @return the telefone
	 */
	public String getTelefone()
	{
		return telefone;
	}

	/**
	 * @param telefone the telefone to set
	 */
	public void setTelefone( String telefone )
	{
		this.telefone = telefone;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail( String email )
	{
		this.email = email;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
		result = prime * result + ( ( email == null ) ? 0 : email.hashCode() );
		result = prime * result + ( ( telefone == null ) ? 0 : telefone.hashCode() );
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
		Contato other = ( Contato ) obj;
		if ( descricao == null )
		{
			if ( other.descricao != null ) return false;
		}
		else if ( !descricao.equals( other.descricao ) ) return false;
		if ( email == null )
		{
			if ( other.email != null ) return false;
		}
		else if ( !email.equals( other.email ) ) return false;
		if ( telefone == null )
		{
			if ( other.telefone != null ) return false;
		}
		else if ( !telefone.equals( other.telefone ) ) return false;
		return true;
	}
	
}
