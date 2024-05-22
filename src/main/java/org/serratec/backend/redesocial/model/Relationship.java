package org.serratec.backend.redesocial.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "relationship")
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_relacao")
    private Long id;

    @Column(nullable = false)
    private Date dataInicioSeguimento;

    @OneToMany(mappedBy = "relationship", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UsuarioRelationship> usuarioPerfis = new HashSet<>();

    public Relationship() {
    }

    public Relationship(Long id, Date dataInicioSeguimento) {
        this.id = id;
        this.dataInicioSeguimento = dataInicioSeguimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataInicioSeguimento() {
        return dataInicioSeguimento;
    }

    public void setDataInicioSeguimento(Date dataInicioSeguimento) {
        this.dataInicioSeguimento = dataInicioSeguimento;
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