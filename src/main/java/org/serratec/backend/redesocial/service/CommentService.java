package org.serratec.backend.redesocial.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.serratec.backend.redesocial.DTO.CommentDTO;
import org.serratec.backend.redesocial.exception.NotFoundException;
import org.serratec.backend.redesocial.exception.UnauthorizedException;
import org.serratec.backend.redesocial.model.Comment;
import org.serratec.backend.redesocial.model.Post;
import org.serratec.backend.redesocial.model.Usuario;
import org.serratec.backend.redesocial.repository.CommentRepository;
import org.serratec.backend.redesocial.repository.PostRepository;
import org.serratec.backend.redesocial.services.UsuarioDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	UsuarioDetailsImp detailsImp;

	public Page<CommentDTO> listar(Pageable pageable) {
		Page<Comment> comments = commentRepository.findAll(pageable);
		if (comments.isEmpty()) {
			throw new NotFoundException("Nenhum comentario encontrado");
		}
		return comments.map(CommentDTO::new);
	}

	public CommentDTO buscarPorId(Long id) {
		Optional<Comment> commentOpt = commentRepository.findById(id);
		Comment comment = commentOpt.orElseThrow(() -> new NotFoundException("Comentario não encontrado"));
		return new CommentDTO(comment);
	}

	@Transactional
	public CommentDTO inserir(CommentDTO commentDTO, Long postId) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails details = detailsImp.loadUserByUsername(username);

		if (!(details instanceof Usuario)) {
			throw new UnauthorizedException("Usuário não encontrado");
		}

		Usuario usuario = (Usuario) details;

		Post post = postRepository.findByIdAndUsuarioId(postId, usuario.getId())
				.orElseThrow(() -> new NotFoundException("Post não encontrado"));

		Comment comment = new Comment();
		comment.setConteudo(commentDTO.getConteudo());
		comment.setDataComentario(LocalDateTime.now());
		comment.setPost(post);

		Comment commentSalvo = commentRepository.save(comment);
		return new CommentDTO(commentSalvo);
	}

	@Transactional
	public CommentDTO atualizar(CommentDTO commentDTO, Long commentId) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails details = detailsImp.loadUserByUsername(username);

		if (!(details instanceof Usuario)) {
			throw new UnauthorizedException("Usuário não encontrado");
		}

		Usuario usuario = (Usuario) details;

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new NotFoundException("Comentário não encontrado"));

		if (!comment.getPost().getUsuario().equals(usuario)) {
			throw new UnauthorizedException("Usuário não autorizado a atualizar o comentário");
		}

		comment.setConteudo(commentDTO.getConteudo());
		comment.setDataComentario(LocalDateTime.now());

		Comment commentAtualizado = commentRepository.save(comment);

		return new CommentDTO(commentAtualizado);

	}

	@Transactional
	public void deletar(long id) {
		Comment commentExistente = commentRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Comentario não encontrado"));
		commentRepository.delete(commentExistente);
	}

}
