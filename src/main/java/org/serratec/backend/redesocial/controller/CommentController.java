package org.serratec.backend.redesocial.controller;

import java.net.URI;

import org.serratec.backend.redesocial.DTO.CommentDTO;
import org.serratec.backend.redesocial.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/comments")
@Validated
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@GetMapping
	@Operation(summary = "Lista todos os comentarios por paginação",
		description = "Essa requisição irá listar todos os comentarios separados por páginas.")
	public ResponseEntity<Page<CommentDTO>> listar(Pageable pageable){
		Page<CommentDTO> commentDTO = commentService.listar(pageable);
		return ResponseEntity.ok(commentDTO);
	}
	
	@GetMapping("/{commentId}")
	@Operation(summary = "Lista os comentarios por id",
		description = "Essa requisição irá listar os comentarios por id.")
	public ResponseEntity<CommentDTO> buscarPorId(@PathVariable Long commentId){
		CommentDTO commentDTO = commentService.buscarPorId(commentId);
		return ResponseEntity.ok(commentDTO);
	}
	
	@PostMapping("/posts/{postId}")
	@Operation(summary = "Salvar novo comentario",
		description = "Essa requisição irá salvar um novo comentario.")
	public ResponseEntity<CommentDTO> inserir(@Valid @RequestBody CommentDTO commentDTO, @PathVariable Long postId){
		CommentDTO novoComment = commentService.inserir(commentDTO, postId);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(novoComment.getId())
				.toUri();
		return ResponseEntity.created(uri).body(novoComment);
	}
	
	@PutMapping("/{commentId}")
	@Operation(summary = "Atualizar comentarios",
		description = "Essa requisição irá atualizar os comentarios existentes.")
	public ResponseEntity<CommentDTO> atualizar(@Valid @RequestBody CommentDTO commentDTO,
												@PathVariable Long commentId){
		
		CommentDTO commentDTOAtualizado = commentService.atualizar(commentDTO, commentId);
		return ResponseEntity.ok(commentDTOAtualizado);
	}
	
	@DeleteMapping("/{commentId}")
	@Operation(summary = "Deletar comentario",
		description = "Essa requisição irá deletar um comentario existente pelo seu ID.")
	public ResponseEntity<Void> deletar(@PathVariable Long commentId){
		commentService.deletar(commentId);
		return ResponseEntity.noContent().build();
	}
	
}
