package org.serratec.backend.redesocial.DTO;

import java.time.LocalDateTime;

import org.serratec.backend.redesocial.model.Post;

public class PostDTO {

	private Long id;
	private String conteudo;
	private LocalDateTime dataPost;
	private Long usuarioId;

	public PostDTO() {
	}

	public PostDTO(Long id, String conteudo, LocalDateTime dataPost, Long usuarioId) {
		this.id = id;
		this.conteudo = conteudo;
		this.dataPost = dataPost;
		this.usuarioId = usuarioId;
	}

	public PostDTO(Post post) {
		this.id = post.getId();
		this.conteudo = post.getConteudo();
		this.dataPost = post.getDataPost();
		this.usuarioId = post.getUsuario().getId();
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

	public LocalDateTime getDataPost() {
		return dataPost;
	}

	public void setDataPost(LocalDateTime dataPost) {
		this.dataPost = dataPost;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	
}
