package org.serratec.backend.redesocial.controller;

import java.net.URI;
import java.util.List;

import org.serratec.backend.redesocial.DTO.UsuarioRelationshipDTO;
import org.serratec.backend.redesocial.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/relationships")
public class RelationshipController {
	@Autowired
	private RelationshipService relationshipService;
		
	// Endpoints relacionamento entre usuarios

	// Endpoint para seguir um usuário
	@PostMapping("/follow/{followedId}")
	@Operation(summary = "Seguir usuario", description = "Essa requisição irá criar um relacionamento entre os usuario(seguido e seguidor).")
	public ResponseEntity<Void> seguirUsuario(@PathVariable Long followedId)throws NotFoundException {
		relationshipService.seguirUsuario(followedId);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{followedId}")
				.buildAndExpand(followedId)
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	// Endpoint para deixar de seguir um usuário
	@DeleteMapping("/unfollow/{followedId}")
	@Operation(summary = "Deixar de seguir usuario", description = "Essa requisição irá deletar um relacionamento entre os usuario(seguido e seguidor).")
	public ResponseEntity<Void> deixarDeSeguirUsuario(@PathVariable Long followedId)
			throws NotFoundException {
		relationshipService.deixarDeSeguirUsuario(followedId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/seguidos")
	@Operation(summary = "Lista todos os usuarios seguidos", description = "Essa requisição ira listar os usuarios seguidos")
	public ResponseEntity<List<UsuarioRelationshipDTO>> UsuariosSeguidos() {		
		List<UsuarioRelationshipDTO> usuariosSeguidosDTO = relationshipService.seguindo();
		return new ResponseEntity<>(usuariosSeguidosDTO, HttpStatus.OK);
	}

	@GetMapping("/seguidores")
	@Operation(summary = "Lista todos os usuarios seguidores", description = "Essa requisição ira listar os usuarios seguidores")
	public ResponseEntity<List<UsuarioRelationshipDTO>> UsuariosSeguidores() {
		List<UsuarioRelationshipDTO> usuariosSeguidosDTO = relationshipService.seguidores();
		return new ResponseEntity<>(usuariosSeguidosDTO, HttpStatus.OK);
	}

}
