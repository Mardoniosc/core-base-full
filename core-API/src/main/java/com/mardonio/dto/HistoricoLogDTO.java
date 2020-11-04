package com.mardonio.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.mardonio.domain.HistoricoLog;

public class HistoricoLogDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Date dataAcesso;
	private Integer usuarioId;
	private String loginIp;
	private String operacao;
	private String tabela;
	
	public HistoricoLogDTO(HistoricoLog obj){
	  id = obj.getId();
	  dataAcesso = obj.getDataAcesso();
	  usuarioId = obj.getUsuario().getId();
	  loginIp = obj.getLoginIp();
	  operacao = obj.getOperacao();
	  tabela = obj.getTabela();
	}

	public HistoricoLogDTO() {}

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

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public String getTabela() {
		return tabela;
	}

	public void setTabela(String tabela) {
		this.tabela = tabela;
	}

}
