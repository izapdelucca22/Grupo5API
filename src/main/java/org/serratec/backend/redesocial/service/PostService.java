package org.serratec.backend.redesocial.service;

import org.serratec.backend.redesocial.DTO.PostDTO;
import org.serratec.backend.redesocial.exception.NotFoundException;
import org.serratec.backend.redesocial.exception.UnauthorizedException;
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

import jakarta.transaction.Transactional;

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

	@Transactional
	public PostDTO inserir(PostDTO postDTO) {

		// Obtém o email de usuário do contexto de segurança
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		// Carrega os detalhes do usuário usando o serviço de detalhes do usuário
		UserDetails details = detailsImp.loadUserByUsername(username);

		// Verifica se o usuário logado é uma instância de Usuario | se naõ encontrar o usuario lança a exception
		if (!(details instanceof Usuario)) {
			throw new UnauthorizedException("Usuário não encontrado");
		}
			
		// Faz o cast seguro para Usuario
			Usuario usuario = (Usuario) details;
			
			Post post = new Post();
			post.setConteudo(postDTO.getConteudo());
			post.setDataPost(LocalDateTime.now());
			post.setUsuario(usuario);

			Post postSalvo = postRepository.save(post);
			return new PostDTO(postSalvo);
	}

	@Transactional
	public PostDTO atualizar(Long id, PostDTO postDTO) {
		Post postExistente = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post não encontrdo"));

		postExistente.setConteudo(postDTO.getConteudo());
		postExistente.setDataPost(LocalDateTime.now());
		postExistente.setUsuario(postExistente.getUsuario());
		postExistente.setId(id);

		Post postAtualizado = postRepository.save(postExistente);
		return new PostDTO(postAtualizado);
	}
	
	@Transactional
	public void deletar(Long id) {
		Post postExistente = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post não encontrdo"));
		postRepository.delete(postExistente);
	}

	// metodo para listar post por idade usando uma native query | TESTAR |
	public Page<Object[]> listarPostPorIdade(int idade, Pageable pageable) {
		Page<Object[]> posts = postRepository.listarPostPorIdade(idade, pageable);
		if (posts.isEmpty()) {
			throw new NotFoundException("Nenhum post encontrado para a idade informada: " + idade);
		}
		return posts;
	}

}
