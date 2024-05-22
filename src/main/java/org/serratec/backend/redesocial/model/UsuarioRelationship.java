package org.serratec.backend.redesocial.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario_relationship")
public class UsuarioRelationship {

    @EmbeddedId
    private UsuarioRelationshipPK id = new UsuarioRelationshipPK();

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @MapsId("relationshipId")
    @JoinColumn(name = "id_relacao")
    private Relationship relationship;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    public UsuarioRelationship() {
    }

    public UsuarioRelationship(Usuario usuario, Relationship relationship, LocalDate dataCriacao) {
        this.usuario = usuario;
        this.relationship = relationship;
        this.dataCriacao = dataCriacao;
        this.id = new UsuarioRelationshipPK(usuario.getId(), relationship.getId());
    }

    public UsuarioRelationshipPK getId() {
        return id;
    }

    public void setId(UsuarioRelationshipPK id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}