package com.mardonio.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "perfil")
public class Perfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	@OneToMany(mappedBy = "id.perfil")
	private Set<PerfilPermissao> perfilsPermissoes = new HashSet<>();
	
	@JsonIgnore
	public Set<PerfilPermissao> getPerfilsPermissoes() {
		return perfilsPermissoes;
	}

	public void setPerfilsPermissoes(Set<PerfilPermissao> perfilsPermissoes) {
		this.perfilsPermissoes = perfilsPermissoes;
	}
	
	public List<Permissao> getPermissoes() {
		List<Permissao> lista = new ArrayList<>();
		for(PerfilPermissao x : perfilsPermissoes) {
			lista.add(x.getPermissao());
		}
		
		return lista;
	}

	public Perfil() {}
	
	public Perfil(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
  
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Perfil other = (Perfil) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

  
}