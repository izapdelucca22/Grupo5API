package org.serratec.backend.redesocial.service;

import org.serratec.backend.redesocial.DTO.PostDTO;
import org.serratec.backend.redesocial.exception.NotFoundException;
import org.serratec.backend.redesocial.model.Post;
import org.serratec.backend.redesocial.model.Usuario;
import org.serratec.backend.redesocial.repository.PostRepository;
import org.serratec.backend.redesocial.services.UsuarioDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	UsuarioDetailsImp detailsImp;

	public Page<PostDTO> listar(Pageable pageable) {
		Page<Post> posts = postRepository.findAll(pageable);
		if (posts.isEmpty()) {
			throw new NotFoundException("Nenhum post encontrado");
		}
		return posts.map(PostDTO::new);
	}

	public PostDTO buscarPorId(Long id) {
		Optional<Post> postOpt = postRepository.findById(id);
		Post post = postOpt.orElseThrow(()-> new NotFoundException("Post não encontrado"));
		return new PostDTO(post);
	}

	public PostDTO inserir(PostDTO postDTO) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails details = detailsImp.loadUserByUsername(username);

		if (details instanceof Usuario) {
			Usuario usuario = (Usuario) details;

			Post post = new Post();
			post.setConteudo(postDTO.getConteudo());
			post.setDataPost(LocalDateTime.now());
			post.setUsuario(usuario);

			Post postSalvo = postRepository.save(post);
			return new PostDTO(postSalvo);
		} else {
			throw new RuntimeException("Usuário logado não encontrado");
		}
	}

	public PostDTO atualizar(Long id, PostDTO postDTO) {
		Post postExistente = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post não encontrdo"));

		postExistente.setConteudo(postDTO.getConteudo());
		postExistente.setDataPost(LocalDateTime.now());
		postExistente.setUsuario(postExistente.getUsuario());
		postExistente.setId(id);

		Post postAtualizado = postRepository.save(postExistente);
		return new PostDTO(postAtualizado);
	}

	// metodo para listar post por idade usando uma native query | TESTAR |
	public Page<Object[]> listarPostPorIdade(int idade, Pageable pageable) {
		Page<Object[]> posts = postRepository.listarPostPorIdade(idade, pageable);
		if (posts.isEmpty()) {
			throw new NotFoundException("Nenhum post encontrado para a idade informada: " + idade);
		}
		return posts;
	}

//	private String getUsernameLogado() {
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		if (principal instanceof UserDetails) {
//			return ((UserDetails) principal).getUsername();
//		} else {
//			throw new RuntimeException("Nenhum usuário logado");
//		}
//	}

}
