package br.udc.engenharia.agenda.domain.entity.compromisso;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import br.udc.engenharia.agenda.domain.entity.AbstractEntity;
import br.udc.engenharia.agenda.domain.entity.account.User;

/**
 * @author Henrique
 *
 */

@Entity
public class CompartilhamentoCompromisso extends AbstractEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4845909891764099076L;

	/**
	 * 
	 */
	@NotNull
	@OneToOne
	private Compromisso compromisso;

	/**
	 * 
	 */
	@NotNull
	@OneToOne
	private User usuario;
	
	/**
	 * 
	 */
	@NotNull
	@OneToOne
	private User autor;

	/**
	 * 
	 */
	public CompartilhamentoCompromisso()
	{
	}

	/**
	 * 
	 * @param id
	 */
	public CompartilhamentoCompromisso( Long id )
	{
		super( id );
	}

	/**
	 * @param compromisso
	 * @param usuario
	 */
	public CompartilhamentoCompromisso( Long id, Compromisso compromisso, User usuario, User autor )
	{
		super( id );
		this.compromisso = new Compromisso( compromisso.getId(), compromisso.getTitulo() );
		this.usuario = new User( usuario.getId(), usuario.getName(), usuario.getEmail() );
		this.autor = new User( autor.getId(), autor.getName(), autor.getEmail() );
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
	 * @return the autor
	 */
	public User getAutor()
	{
		return autor;
	}

	/**
	 * @param autor the autor to set
	 */
	public void setAutor( User autor )
	{
		this.autor = autor;
	}

}
