package com.mardonio.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "perfil_permissao")
public class PerfilPermissao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private PerfilPermissaoPK id = new PerfilPermissaoPK();
	
	private int status;
	
	public PerfilPermissao() {}

	public PerfilPermissao(Perfil perfil, Permissao permissao, int status) {
		super();
		id.setPerfil(perfil);
		id.setPermissao(permissao);
		this.status = status;
	}
	
	public Perfil getPerfil() {
		return id.getPerfil();
	}
	
	@JsonIgnore
	public Permissao getPermissao() {
		return id.getPermissao();
	}

	public PerfilPermissaoPK getId() {
		return id;
	}

	public void setId(PerfilPermissaoPK id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
		PerfilPermissao other = (PerfilPermissao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
