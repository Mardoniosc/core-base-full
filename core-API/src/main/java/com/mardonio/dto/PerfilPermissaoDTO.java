package com.mardonio.dto;

import java.io.Serializable;

import com.mardonio.domain.PerfilPermissao;

public class PerfilPermissaoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer perfilId;
	private Integer permissaoId;
	private Integer status;
	
	public PerfilPermissaoDTO() {}
	
	public PerfilPermissaoDTO(PerfilPermissao obj) {
		perfilId = obj.getId().getPerfil().getId();
		permissaoId = obj.getId().getPermissao().getId();
		status = obj.getStatus();
	}

	public Integer getPerfilId() {
		return perfilId;
	}

	public void setPerfilId(Integer perfilId) {
		this.perfilId = perfilId;
	}

	public Integer getPermissaoId() {
		return permissaoId;
	}

	public void setPermissaoId(Integer permissaoId) {
		this.permissaoId = permissaoId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
