package com.mardonio.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;

	@Column(unique = true)
	private String email;
	
	private String senha;
	
	private Date dataNascimento;
	
	private String cpf;
	
	private String login;

	private String imagem;
	
	private Date criado;

	private int status;
	
	private byte[] imageProfile;
	
	@ManyToOne
	@JoinColumn(name = "perfil_id")
	private Perfil perfil;

	@OneToMany(mappedBy = "usuario")
	private Set<HistoricoAcesso> historicosAcesso = new HashSet<>();
	
	@OneToMany(mappedBy = "usuario")
	private Set<HistoricoLog> historicosLog = new HashSet<>();
	
	@OneToMany(mappedBy = "usuario")
	private Set<InforUserIp> inforUserIp = new HashSet<>();
	
	@JsonIgnore
	public Set<HistoricoLog> getHistoricosLog() {
		return historicosLog;
	}

	public void setHistoricosLog(Set<HistoricoLog> historicosLog) {
		this.historicosLog = historicosLog;
	}
	
	@JsonIgnore
	public Set<InforUserIp> getInforUserIp() {
		return inforUserIp;
	}

	public void setInforUserIp(Set<InforUserIp> inforUserIp) {
		this.inforUserIp = inforUserIp;
	}

	@JsonIgnore
	public Set<HistoricoAcesso> getHistoricosAcesso() {
		return historicosAcesso;
	}

	public void setHistoricosAcesso(Set<HistoricoAcesso> historicosAcesso) {
		this.historicosAcesso = historicosAcesso;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Usuario() {}

	public Usuario(Integer id, String nome, String email, String senha, String cpf, String login, String imagem,
			Date criado, int status, Date dataNascimento, byte[] imageProfile, Perfil perfil) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cpf = cpf;
		this.login = login;
		this.imagem = imagem;
		this.criado = criado;
		this.status = status;
		this.dataNascimento = dataNascimento;
		this.imageProfile = imageProfile;
		this.perfil = perfil;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Date getCriado() {
		return criado;
	}

	public void setCriado(Date criado) {
		this.criado = criado;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public byte[] getImageProfile() {
		return imageProfile;
	}

	public void setImageProfile(byte[] imageProfile) {
		this.imageProfile = imageProfile;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
