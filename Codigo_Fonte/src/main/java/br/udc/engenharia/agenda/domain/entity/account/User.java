package br.udc.engenharia.agenda.domain.entity.account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.udc.engenharia.agenda.domain.entity.AbstractEntity;

/**
 * 
 * @since 02/06/2014
 * @version 1.0
 * @category
 
 */
@Entity
@Table(name = "\"user\"")
@DataTransferObject
public class User extends AbstractEntity implements Serializable, UserDetails
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4052986759552589018L;

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	// Basic
	/**
	 * 
	 */
	@NotEmpty
	@Column(nullable = false, length = 50)
	private String name;
	/**
	 * 
	 */
	@Email
	@NotNull
	@Column(nullable = false, length = 144, unique = true)
	private String email;
	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	private Boolean enabled;
	/**
	 * 
	 */
	@NotBlank
	@Length(min = 8)
	@Column(nullable = false, length = 100)
	private String password;
	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private UserRole role;

	/**
	 * 
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Contato> contatos = new ArrayList<Contato>();

	/*-------------------------------------------------------------------
	 * 		 					CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	public User()
	{
	}

	/**
	 * 
	 * @param id
	 */
	public User( Long id )
	{
		super( id );
	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param email
	 */
	public User( Long id, String name, String email)
	{
		super( id );
		this.email = email;
		this.name = name;
	}
	/**
	 * 
	 * @param id
	 * @param name
	 * @param email
	 * @param enabled
	 */
	public User( Long id, String name, String email, boolean enabled )
	{
		super( id );
		this.email = email;
		this.name = name;
		this.enabled = enabled;
	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param email
	 * @param enabled
	 * @param role
	 */
	public User( Long id, String name, String email, boolean enabled, UserRole role )
	{
		super( id );
		this.email = email;
		this.name = name;
		this.enabled = enabled;
		this.role = role;
	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param email
	 * @param enabled
	 * @param role
	 * @param password
	 * @param contatos
	 */
	public User( Long id, String name, String email, boolean enabled, UserRole role, String password, List<Contato> contatos )
	{
		super( id );
		this.email = email;
		this.name = name;
		this.enabled = enabled;
		this.password = password;
		this.role = role;
		this.contatos = contatos;
	}

	/*-------------------------------------------------------------------
	 *							BEHAVIORS
	 *-------------------------------------------------------------------*/

	/*-------------------------------------------------------------------
	 *						GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Override
	@Transient
	public Set<GrantedAuthority> getAuthorities()
	{
		final Set<GrantedAuthority> authorities = new HashSet<>();

		if ( role == null )
		{
			return null;
		}

		if ( role.equals( UserRole.ADMINISTRATOR ) )
		{
			authorities.add( UserRole.ADMINISTRATOR );
			authorities.add( UserRole.MANAGER );
		}

		if ( role.equals( UserRole.MANAGER ) )
		{
			authorities.add( UserRole.MANAGER );
		}
		authorities.add( UserRole.USER );

		return authorities;
	}

	/**
	 * 
	 */
	@Override
	@Transient
	public boolean isAccountNonExpired()
	{
		return true;
	}

	/**
	 * 
	 */
	@Override
	@Transient
	public boolean isAccountNonLocked()
	{
		return true;
	}

	/**
	 * 
	 */
	@Override
	@Transient
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	/**
	 * 
	 */
	@Override
	@Transient
	public boolean isEnabled()
	{
		return this.enabled;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	@Override
	@Transient
	public String getPassword()
	{
		return this.password;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Override
	@Transient
	public String getUsername()
	{
		return this.email;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail( String email )
	{
		this.email = email;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled()
	{
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled( Boolean enabled )
	{
		this.enabled = enabled;
	}

	/**
	 * @return the role
	 */
	public UserRole getRole()
	{
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole( UserRole userRole )
	{
		this.role = userRole;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword( String password )
	{
		this.password = password;
	}

	/**
	 * @return the contatos
	 */
	public List<Contato> getContatos()
	{
		return contatos;
	}

	/**
	 * @param contatos the contatos to set
	 */
	public void setContatos( List<Contato> contatos )
	{
		this.contatos = contatos;
	}
	
	
}