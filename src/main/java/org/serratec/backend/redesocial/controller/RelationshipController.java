package org.serratec.backend.redesocial.controller;

import org.serratec.backend.redesocial.model.Relationship;
import org.serratec.backend.redesocial.service.RelationshipService;
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
@RequestMapping("/relationships")
public class RelationshipController {
	@Autowired
	private RelationshipService relationshipService;

	@GetMapping
	@Operation(summary = "Lista os perfis dos usuários", description = "Essa requisição irá listar todos os perfis.")
	public List<Relationship> getAllRelationships() {
		return relationshipService.findAll();
	}

	// SUPORTE PARA PAGINACAO
	@GetMapping("/paged")
	@Operation(summary = "Lista os perfis por paginação", description = "Essa requisição irá listar todos os perfis por paginação.")
	public Page<Relationship> getAllRelationships(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 8) Pageable pageable) {
		return relationshipService.findAll(pageable);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Lista os perfis por id", description = "Essa requisição irá listar os perfis por id.")
	public ResponseEntity<Relationship> getRelationshipById(@PathVariable Long id) {
		Optional<Relationship> relationship = relationshipService.findById(id);
		return relationship.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	@Operation(summary = "Salvar Perfil", description = "Essa requisição irá salvar novos perfis.")
	public Relationship createRelationship(@RequestBody Relationship relationship) {
		return relationshipService.save(relationship);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deletar Perfil", description = "Essa requisição irá deletar um perfil existente.")
	public ResponseEntity<Void> deleteRelationship(@PathVariable Long id) {
		relationshipService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualizar Perfil", description = "Essa requisição irá atualizar um perfil existente.")
	public ResponseEntity<Relationship> updatePost(@PathVariable Long id,
			@Valid @RequestBody Relationship relationship) {
		if (!relationshipService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		relationship.setId(id);
		relationship = relationshipService.save(relationship);
		return ResponseEntity.ok(relationship);
	}

}
