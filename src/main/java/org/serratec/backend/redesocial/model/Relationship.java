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
	private Set<UsuarioRelationship> usuarioPerfis = new HashSet<>();

	public Relationship(Long id, LocalDateTime dataInicioSeguimento) {
		this.id = id;
		this.dataInicioSeguimento = dataInicioSeguimento;
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
		this.dataInicioSeguimento = LocalDateTime.now();
	}

	public Set<UsuarioRelationship> getUsuarioPerfis() {
		return usuarioPerfis;
	}

	public void setUsuarioPerfis(Set<UsuarioRelationship> usuarioPerfis) {
		this.usuarioPerfis = usuarioPerfis;
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