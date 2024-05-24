package org.serratec.backend.redesocial.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "relationship")
public class Relationship {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_relacao")
	private Long id;

	@Column(nullable = false)
	@NotNull(message = "Data de início do seguimento não pode ser nula")
	private LocalDateTime dataInicioSeguimento;

	@OneToMany(mappedBy = "relationship", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<UsuarioRelationship> usuarioRelationship = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "follower_id", nullable = false)
	private Usuario follower;

	@ManyToOne
	@JoinColumn(name = "followed_id", nullable = false)
	private Usuario followed;

	public Relationship() {}

	public Relationship(Long id, LocalDateTime dataInicioSeguimento, Usuario follower, Usuario followed) {
		this.id = id;
		this.dataInicioSeguimento = dataInicioSeguimento;
		this.follower = follower;
		this.followed = followed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataInicioSeguimento() {
		return dataInicioSeguimento;
	}

	public void setDataInicioSeguimento(LocalDateTime dataInicioSeguimento) {
		this.dataInicioSeguimento = dataInicioSeguimento;
	}

	public Set<UsuarioRelationship> getUsuarioPerfis() {
		return usuarioRelationship;
	}

	public void setUsuarioPerfis(Set<UsuarioRelationship> usuarioRelationship) {
		this.usuarioRelationship = usuarioRelationship;
	}

	public Usuario getFollower() {
		return follower;
	}

	public void setFollower(Usuario follower) {
		this.follower = follower;
	}

	public Usuario getFollowed() {
		return followed;
	}

	public void setFollowed(Usuario followed) {
		this.followed = followed;
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
		Relationship other = (Relationship) obj;
		return Objects.equals(id, other.id);
	}
}