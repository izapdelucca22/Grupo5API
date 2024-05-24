package org.serratec.backend.redesocial.controller;

import java.net.URI;

import org.serratec.backend.redesocial.DTO.PostDTO;
import org.serratec.backend.redesocial.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/posts")
@Validated
public class PostController {
	
	@Autowired
	private PostService postService;

	// MOSTRA POSTS COM PAGINACAO
	@GetMapping
	@Operation(summary = "Lista todos os Posts por paginação", description = "Essa requisição irá listar todos os posts separados por páginas.")
	public ResponseEntity<Page<PostDTO>> listar(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 8) Pageable pageable) {
		return ResponseEntity.ok(postService.listar(pageable));
	}

	// MOSTRA UM POST ESPECIFICO PELO ID DO MESMO
	@GetMapping("/{id}")
	@Operation(summary = "Lista os posts por id", description = "Essa requisição irá listar os posts por id.")
	public ResponseEntity<PostDTO> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(postService.buscarPorId(id));
	}
	
	@PostMapping
	@Operation(summary = "Salvar novo post", description = "Essa requisição irá salvar um novo post.")
	public ResponseEntity<PostDTO> inserir(@Valid @RequestBody PostDTO postDTO){
		PostDTO novoPostDTO = postService.inserir(postDTO);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/id")
				.buildAndExpand(novoPostDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(novoPostDTO);
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Atualizar posts", description = "Essa requisição irá atualizar posts existentes.")
	public ResponseEntity<PostDTO> atualizar(@Valid @RequestBody PostDTO postDTO,  @PathVariable Long id){
		PostDTO postDTOAtualizado = postService.atualizar(id, postDTO);
		return ResponseEntity.ok(postDTOAtualizado);
	}

	@GetMapping("/idade/{idade}")
	@Operation(summary = "Lista posts por tempo", description = "Essa requisição irá listar os posts por tempo.")
	public Page<Object[]> listarPostPorIdade(@PathVariable int idade, Pageable pageable) {
		return postService.listarPostPorIdade(idade, pageable);
	}

}