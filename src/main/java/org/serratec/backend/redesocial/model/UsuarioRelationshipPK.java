package org.serratec.backend.redesocial.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UsuarioRelationshipPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id_usuario")
    private Long usuarioId;

    @Column(name = "id_relacao")
    private Long relationshipId;

    public UsuarioRelationshipPK() {
    }

    public UsuarioRelationshipPK(Long usuarioId, Long relationshipId) {
        this.usuarioId = usuarioId;
        this.relationshipId = relationshipId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, relationshipId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UsuarioRelationshipPK other = (UsuarioRelationshipPK) obj;
        return Objects.equals(usuarioId, other.usuarioId) && Objects.equals(relationshipId, other.relationshipId);
    }
}