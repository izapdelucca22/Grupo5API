package org.serratec.backend.redesocial.DTO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.serratec.backend.redesocial.model.Perfil;
import org.serratec.backend.redesocial.model.Usuario;
import org.serratec.backend.redesocial.model.UsuarioPerfil;

import com.fasterxml.jackson.annotation.JsonFormat;


public class UsuarioDTO {

	private Long id;

	private String nome;

	private String email;
	
	private String url;
	
	private String senha;

	private String confirmaSenha;
	
	

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataNascimento;

	private Set<Perfil> perfis;

	public UsuarioDTO() {
	}

	
	public UsuarioDTO(Long id, String nome, String email, String url, Date dataNascimento) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.url = url;
		this.dataNascimento = dataNascimento;
	}

	public UsuarioDTO(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.dataNascimento = usuario.getDataNascimento();
		this.perfis = new HashSet<>();
		for (UsuarioPerfil usuarioPerfil: usuario.getUsuarioPerfis()) {
			this.perfis.add(usuarioPerfil.getId().getPerfil());
		}
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
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


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public Date getDataNascimento() {
		return dataNascimento;
	}


	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}


	public Set<Perfil> getPerfis() {
		return perfis;
	}


	public void setPerfis(Set<Perfil> perfis) {
		this.perfis = perfis;
	}
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}


}
