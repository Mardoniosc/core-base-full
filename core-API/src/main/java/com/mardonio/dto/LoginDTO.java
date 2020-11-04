package com.mardonio.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class LoginDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public LoginDTO() {}
	
	@Email(message =  "O e-mail informado é inválido")
	@NotEmpty(message = "Preenchimento obrigatório")
	private String login;
	
	@NotEmpty(message = "Obrigatório informar a senha")
	@Length(min = 6, message = "Senha deve conter no mínimo 6 caracteres")
	private String senha;
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	
	

}
