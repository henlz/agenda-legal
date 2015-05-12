package br.udc.engenharia.agenda.domain.entity.compromisso;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.validator.constraints.NotEmpty;

import br.udc.engenharia.agenda.domain.entity.AbstractEntity;
import br.udc.engenharia.agenda.domain.entity.account.User;

/**
 * @author Henrique L. Zago
 * @since 31/03/2015
 *
 */
@Entity
@DataTransferObject(javascript = "Compromisso")
public class Compromisso extends AbstractEntity implements Serializable
{


	/**
	 * 
	 */
	private static final long serialVersionUID = 291418597900863459L;

	/**
	 * 
	 */
	@NotEmpty
	@Column
	private String titulo;

	/**
	 * 
	 */
	@Column
	private String descricao;

	/**
	 * 
	 */
	@Column
	private String observacoes;

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
	@Column
	private CompromissoFrenquencia frequencia;

	/**
	 * 
	 */
	@Column
	private Status status;

	/**
	 * 
	 */
	@ManyToOne
	private User usuario;

	/**
	 * 
	 */
	@ManyToOne
	private CategoriaCompromisso categoriaCompromisso;

	/**
	 * 
	 */
	@ManyToOne
	private TipoCompromisso tipoCompromisso;

	/**
	 * 
	 */
	@Transient
	private List<Agenda> agendas;

	/**
	 * 
	 */
	public Compromisso()
	{
		super();
	}

	/**
	 * 
	 * @param id
	 */
	public Compromisso( Long id )
	{
		super( id );
	}

	/**
	 * @param titulo
	 * @param descricao
	 * @param observacoes
	 * @param dataInicio
	 * @param dataFim
	 * @param frequencia
	 * @param status
	 */
	public Compromisso( String titulo, String descricao, String observacoes, Date dataInicio, Date dataFim, CompromissoFrenquencia frequencia, Status status, User usuario )
	{
		super();
		this.titulo = titulo;
		this.descricao = descricao;
		this.observacoes = observacoes;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.frequencia = frequencia;
		this.status = status;
		this.usuario = usuario;
	}

	/**
	 * @param titulo
	 * @param descricao
	 * @param observacoes
	 * @param dataInicio
	 * @param dataFim
	 * @param frequencia
	 * @param status
	 */
	public Compromisso( Long id, String titulo, String descricao, String observacoes, Date dataInicio, Date dataFim, CompromissoFrenquencia frequencia, Status status, User usuario, TipoCompromisso tipoCompromisso, CategoriaCompromisso categoriaCompromisso )
	{
		super( id );
		this.titulo = titulo;
		this.descricao = descricao;
		this.observacoes = observacoes;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.frequencia = frequencia;
		this.status = status;
		this.usuario = usuario;
		this.tipoCompromisso = tipoCompromisso;
		this.categoriaCompromisso = categoriaCompromisso;
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
		result = prime * result + ( ( dataFim == null ) ? 0 : dataFim.hashCode() );
		result = prime * result + ( ( dataInicio == null ) ? 0 : dataInicio.hashCode() );
		result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
		result = prime * result + ( ( frequencia == null ) ? 0 : frequencia.hashCode() );
		result = prime * result + ( ( observacoes == null ) ? 0 : observacoes.hashCode() );
		result = prime * result + ( ( status == null ) ? 0 : status.hashCode() );
		result = prime * result + ( ( titulo == null ) ? 0 : titulo.hashCode() );
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
		Compromisso other = ( Compromisso ) obj;
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
		if ( descricao == null )
		{
			if ( other.descricao != null ) return false;
		}
		else if ( !descricao.equals( other.descricao ) ) return false;
		if ( frequencia != other.frequencia ) return false;
		if ( observacoes == null )
		{
			if ( other.observacoes != null ) return false;
		}
		else if ( !observacoes.equals( other.observacoes ) ) return false;
		if ( status != other.status ) return false;
		if ( titulo == null )
		{
			if ( other.titulo != null ) return false;
		}
		else if ( !titulo.equals( other.titulo ) ) return false;
		if ( usuario == null )
		{
			if ( other.usuario != null ) return false;
		}
		else if ( !usuario.equals( other.usuario ) ) return false;
		return true;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo()
	{
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo( String titulo )
	{
		this.titulo = titulo;
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
	 * @return the observacoes
	 */
	public String getObservacoes()
	{
		return observacoes;
	}

	/**
	 * @param observacoes the observacoes to set
	 */
	public void setObservacoes( String observacoes )
	{
		this.observacoes = observacoes;
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
	 * @return the frequencia
	 */
	public CompromissoFrenquencia getFrequencia()
	{
		return frequencia;
	}

	/**
	 * @param frequencia the frequencia to set
	 */
	public void setFrequencia( CompromissoFrenquencia frequencia )
	{
		this.frequencia = frequencia;
	}

	/**
	 * @return the status
	 */
	public Status getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus( Status status )
	{
		this.status = status;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	/**
	 * @return the agendas
	 */
	public List<Agenda> getAgendas()
	{
		return agendas;
	}

	/**
	 * @param agendas the agendas to set
	 */
	public void setAgendas( List<Agenda> agendas )
	{
		this.agendas = agendas;
	}

	/**
	 * @return the categoriaCompromisso
	 */
	public CategoriaCompromisso getCategoriaCompromisso()
	{
		return categoriaCompromisso;
	}

	/**
	 * @param categoriaCompromisso the categoriaCompromisso to set
	 */
	public void setCategoriaCompromisso( CategoriaCompromisso categoriaCompromisso )
	{
		this.categoriaCompromisso = categoriaCompromisso;
	}

	/**
	 * @return the tipoCompromisso
	 */
	public TipoCompromisso getTipoCompromisso()
	{
		return tipoCompromisso;
	}

	/**
	 * @param tipoCompromisso the tipoCompromisso to set
	 */
	public void setTipoCompromisso( TipoCompromisso tipoCompromisso )
	{
		this.tipoCompromisso = tipoCompromisso;
	}

	
}
