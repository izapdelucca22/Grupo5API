package org.serratec.backend.redesocial.DTO;

import java.time.LocalDate;

import org.serratec.backend.redesocial.model.UsuarioRelationship;

public class UsuarioRelationshipDTO {
    private Long usuarioId;
    private Long relationshipId;
    private LocalDate dataCriacao;

    public UsuarioRelationshipDTO() {}

    public UsuarioRelationshipDTO(UsuarioRelationship usuarioRelationship) {
        this.usuarioId = usuarioRelationship.getUsuario().getId();
        this.relationshipId = usuarioRelationship.getRelationship().getId();
        this.dataCriacao = usuarioRelationship.getDataCriacao();
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

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	} 
}