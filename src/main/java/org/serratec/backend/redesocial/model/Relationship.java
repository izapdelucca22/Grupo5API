package org.serratec.backend.redesocial.model;

import java.time.LocalDateTime;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "follower_id", nullable = false)
	private Usuario follower;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "followed_id", nullable = false)
	private Usuario followed;

	// Construtores, getters e setters
	public Relationship() {
	}

	public Relationship(LocalDateTime dataInicioSeguimento, Usuario follower, Usuario followed) {
		this.dataInicioSeguimento = dataInicioSeguimento;
		this.follower = follower;
		this.followed = followed;
	}

	// Getters e Setters
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