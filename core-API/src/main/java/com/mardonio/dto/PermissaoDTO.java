package com.mardonio.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.mardonio.domain.Permissao;

public class PermissaoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
  private Integer id;

  @NotEmpty(message = "Preechimento obrigatório")
  @Length(min = 4, max = 50, message = "Descricação deve conter entre 4 e 50 caracteries")
  private String descricao;

  @NotEmpty(message = "Preechimento obrigatório")
  private String url;

  private Integer permissaoPai;

  public PermissaoDTO(){}

  public PermissaoDTO(Permissao obj) {
    id = obj.getId();
    descricao = obj.getDescricao();
    url = obj.getUrl();
    permissaoPai = obj.getPermissaoPai().getId();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getPermissaoPai() {
    return permissaoPai;
  }

  public void setPermissaoPai(Integer permissaoPai) {
    this.permissaoPai = permissaoPai;
  }

}