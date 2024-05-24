package org.serratec.backend.redesocial.DTO;

import java.time.LocalDateTime;

import org.serratec.backend.redesocial.model.Comment;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommentDTO {

	private Long id;
	

    @NotNull(message = "Preencha o conteúdo")
    @Size(min = 1, max = 120, message = "O conteúdo deve ter entre {min} e {max} letras")
	private String conteudo;
	
	
	private LocalDateTime dataComentario;
	private Long postId;
	
	
	public CommentDTO() {}


	public CommentDTO(Long id, String conteudo, LocalDateTime dataComentario, Long postId) {
		super();
		this.id = id;
		this.conteudo = conteudo;
		this.dataComentario = dataComentario;
		this.postId = postId;
	}
	
	public CommentDTO(Comment comment) {
		super();
		this.id = comment.getId();
		this.conteudo = comment.getConteudo();
		this.dataComentario = comment.getDataComentario();
		this.postId = comment.getPost().getId();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getConteudo() {
		return conteudo;
	}


	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}


	public LocalDateTime getDataComentario() {
		return dataComentario;
	}


	public void setDataComentario(LocalDateTime dataComentario) {
		this.dataComentario = dataComentario;
	}


	public Long getPostId() {
		return postId;
	}


	public void setPostId(Long postId) {
		this.postId = postId;
	}
	
	
}
