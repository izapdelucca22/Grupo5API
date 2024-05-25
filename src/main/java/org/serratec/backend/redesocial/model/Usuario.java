package org.serratec.backend.redesocial.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long id;
	
	@Column(nullable = false)
	@NotBlank(message = "Preencha o nome")
	@Size(min = 2, max = 50, message = "o nome da pessoa deve ter entre {min} e {max} letras")
	private String nome;

	@Column(nullable = false)
	@NotBlank(message = "Preencha o sobrenome")
	@Size(min = 2, max = 50, message = "O sobrenome da pessoa deve ter entre {min} e {max} letras")
	private String sobrenome;

	@Column(nullable = false, unique = true)
	@NotBlank(message = "Preencha o email")
	@Email(message = "Email inválido")
	private String email;

	@Column(nullable = false)
	@NotBlank(message = "Preencha a senha")
	@Size(min = 4)
	private String senha;

	@Column(nullable = false)
	@NotNull(message = "Preencha a data de nascimento")
	@Past(message = "A data de nascimento deve ser no passado")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataNascimento;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<UsuarioRelationship> usuarioRelacoes = new HashSet<>();

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Post> posts = new HashSet<>();

	@OneToMany(mappedBy = "id.usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<UsuarioPerfil> usuarioPerfis = new HashSet<>();

	@OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JsonIgnore
	private Set<Relationship> followers = new HashSet<>();

	@OneToMany(mappedBy = "followed", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JsonIgnore
	private Set<Relationship> followings = new HashSet<>();

	public Usuario() {
	}

	public Usuario(Long id, String nome, String sobrenome, String email, String senha, Date dataNascimento) {
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.email = email;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
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

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Set<UsuarioRelationship> getUsuarioRelacoes() {
		return usuarioRelacoes;
	}

	public void setUsuarioRelacoes(Set<UsuarioRelationship> usuarioRelacoes) {
		this.usuarioRelacoes = usuarioRelacoes;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public Set<UsuarioPerfil> getUsuarioPerfis() {
		return usuarioPerfis;
	}

	public void setUsuarioPerfis(Set<UsuarioPerfil> usuarioPerfis) {
		this.usuarioPerfis = usuarioPerfis;
	}

	public Set<Relationship> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<Relationship> followers) {
		this.followers = followers;
	}

	public Set<Relationship> getFollowings() {
		return followings;
	}

	public void setFollowings(Set<Relationship> followings) {
		this.followings = followings;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		return Objects.equals(id, other.id);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auth = new ArrayList<>();
		for (UsuarioPerfil usuarioPerfil: getUsuarioPerfis()) {
			String perfilNome = usuarioPerfil.getId().getPerfil().getNome();
			if(perfilNome != null && !perfilNome.trim().isEmpty()) {
				auth.add(new SimpleGrantedAuthority(perfilNome));	
			}else {
	            System.out.println("Invalid perfil name: " + perfilNome);
	        }
		}
		return auth;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}