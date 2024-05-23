package org.serratec.backend.redesocial.controller;

import org.serratec.backend.redesocial.model.Post;
import org.serratec.backend.redesocial.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
	@Autowired
	private PostService postService;

	// MOSTRA TODOS OS POSTS
	@GetMapping
	@Operation(summary = "Lista todos os Posts", description = "Essa requisição irá listar todos os posts.")
	public List<Post> getAllPosts() {
		return postService.findAll();
	}

	// MOSTRA POSTS COM PAGINACAO
	@GetMapping("/paged")
	@Operation(summary = "Lista todos os Posts por paginação", description = "Essa requisição irá listar todos os posts separados por páginas.")
	public Page<Post> getAllPosts(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 8) Pageable pageable) {
		return postService.findAll(pageable);
	}

	// MOSTRA UM POST ESPECIFICO PELO ID DO MESMO
	@GetMapping("/{id}")
	@Operation(summary = "Lista os posts por id", description = "Essa requisição irá listar os posts por id.")
	public ResponseEntity<Post> getPostById(@PathVariable Long id) {
		Optional<Post> post = postService.findById(id);
		return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// CRIA UM NOVO POST
	@PostMapping
	@Operation(summary = "Salvar novo post", description = "Essa requisição irá salvar um novo post.")
	public Post createPost(@RequestBody Post post) {
		return postService.save(post);
	}

	// DELETA UM POST ESPECIFICO JA EXISTENTE PELO ID
	@DeleteMapping("/{id}")
	@Operation(summary = "Deletar Post", description = "Essa requisição irá deletar um post existente.")
	public ResponseEntity<Void> deletePost(@PathVariable Long id) {
		postService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/idade/{idade}")
	@Operation(summary = "Lista posts por tempo", description = "Essa requisição irá listar os posts por tempo.")
	public Page<Object[]> listarPostPorIdade(@PathVariable int idade, Pageable pageable) {
		return postService.listarPostPorIdade(idade, pageable);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualizar posts", description = "Essa requisição irá atualizar posts existentes.")
	public ResponseEntity<Post> updatePost(@PathVariable Long id, @Valid @RequestBody Post post) {
		if (!postService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		post.setId(id);
		post = postService.save(post);
		return ResponseEntity.ok(post);
	}

}