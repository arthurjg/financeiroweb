package financeiro.model;

import java.io.Serializable;
import java.util.List;


import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;

import org.hibernate.annotations.*;


@Entity
public class Categoria implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer	      codigo;

	@ManyToOne
	@JoinColumn(name = "categoria_pai", nullable = true)
	@org.hibernate.annotations.ForeignKey(name = "fk_categoria_categoria")
	private Categoria	      pai;

	@ManyToOne
	@OnDelete(action=OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario")
	@org.hibernate.annotations.ForeignKey(name = "fk_categoria_usuario")
	private Usuario	      usuario;

	private String	         descricao;

	private int	            fator;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "categoria_pai", updatable = false)
	@org.hibernate.annotations.OrderBy(clause = "descricao asc")
	private List<Categoria>	filhos;

	public Categoria() {
	}

	public Categoria(Categoria pai, Usuario usuario, String descricao, int fator) {
		this.pai = pai;
		this.usuario = usuario;
		this.descricao = descricao;
		this.fator = fator;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Categoria getPai() {
		return pai;
	}

	public void setPai(Categoria pai) {
		this.pai = pai;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Categoria> getFilhos() {
		return filhos;
	}

	public void setFilhos(List<Categoria> filhos) {
		this.filhos = filhos;
	}

	public int getFator() {
		return fator;
	}

	public void setFator(int fator) {
		this.fator = fator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
