package com.mardonio.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
@Table(name = "permissao")
public class Permissao implements Serializable {
	private static final long serialVersionUID = 1L;
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		private String descricao;
		private String url;
		
		@ManyToOne
		@JoinColumn(name = "permissao_pai_id")
		private Permissao permissaoPai;
		
		public Permissao getPermissaoPai() {
			return permissaoPai;
		}

		public void setPermissaoPai(Permissao permissaoPai) {
			this.permissaoPai = permissaoPai;
		}

		@OneToMany(mappedBy = "id.permissao", cascade = CascadeType.REMOVE)
		private Set<PerfilPermissao> perfilsPermissoes = new HashSet<>();
		
		@JsonIgnore
		public Set<PerfilPermissao> getPerfilsPermissoes() {
			return perfilsPermissoes;
		}

		public void setPerfilsPermissoes(Set<PerfilPermissao> perfilsPermissoes) {
			this.perfilsPermissoes = perfilsPermissoes;
		}
		
		@JsonIgnore
		public List<Perfil> getPerfil() {
			List<Perfil> lista = new ArrayList<>();
			for(PerfilPermissao x : perfilsPermissoes) {
				lista.add(x.getPerfil());
			}
			
			return lista;
		}

		public Permissao() {}

		public Permissao(Integer id, String descricao, String url, Permissao permissaoPai) {
			super();
			this.id = id;
			this.descricao = descricao;
			this.url = url;
			this.permissaoPai = permissaoPai;
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
			Permissao other = (Permissao) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}
		
		
}
