package com.mardonio.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.mardonio.domain.HistoricoAcesso;

public class HistoricoAcessoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Date dataAcesso;
	private Integer usuarioId;
	private String loginIp;
	
	public HistoricoAcessoDTO() {}
		
	public HistoricoAcessoDTO(HistoricoAcesso obj){
	  id = obj.getId();
	  dataAcesso = obj.getDataAcesso();
	  usuarioId = obj.getUsuario().getId();
	  loginIp = obj.getLoginIp();
	}
		
	public Integer getId() {
	  return id;
	}
		
	public void setId(Integer id) {
	  this.id = id;
	}
		
	public Date getDataAcesso() {
	  return dataAcesso;
	}
		
	public void setDataAcesso(Date dataAcesso) {
	  this.dataAcesso = dataAcesso;
	}
		
	public Integer getUsuarioId() {
	  return usuarioId;
	}
		
	public void setUsuarioId(Integer usuarioId) {
	  this.usuarioId = usuarioId;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	
}