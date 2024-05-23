package org.serratec.backend.redesocial.controller;

import org.serratec.backend.redesocial.model.Usuario;
import org.serratec.backend.redesocial.service.UsuarioService;
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
@RequestMapping("/usuarios")
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;

	// MOSTRA TODOS OS USUARIOS
	@GetMapping
	@Operation(summary = "Lista todos os Usuários", description = "Essa requisição irá listar todos os usuários.")
	public List<Usuario> getAllUsers() {
		return usuarioService.findAll();
	}

	// MOSTRA OS USUARIOS COM PAGINACAO
	@GetMapping("/paged")
	@Operation(summary = "Lista os usuários por paginação", description = "Essa requisição irá listar os usuários separados por páginas.")
	public Page<Usuario> getAllUsers(
			@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 8) Pageable pageable) {
		return usuarioService.findAll(pageable);
	}

	// MOSTRA UM USUARIO ESPECIFICO PELO ID DO MESMO
	@GetMapping("/{id}")
	@Operation(summary = "Lista os usuários por id", description = "Essa requisição irá selecionar os usuários por id.")
	public ResponseEntity<Usuario> getUserById(@PathVariable Long id) {
		Optional<Usuario> user = usuarioService.findById(id);
		return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// CRIA UM NOVO USUARIO
	@PostMapping
	@Operation(summary = "Salvar novo usuário", description = "Essa requisição irá salvar um novo usuário.")
	public Usuario createUser(@RequestBody Usuario usuario) {
		return usuarioService.save(usuario);
	}

	// DELETA UM USUARIO JA EXISTENTE PELO ID
	@DeleteMapping("/{id}")
	@Operation(summary = "Deletar um usuário", description = "Essa requisição irá deletar um usuário existente.")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		usuarioService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	// Atualiza um usuário existente
	@PutMapping("/{id}")
	@Operation(summary = "Atualizar um usuário", description = "Essa requisição irá atualizar um usuário existente.")
	public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
		if (!usuarioService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		usuario.setId(id);
		usuario = usuarioService.save(usuario);
		return ResponseEntity.ok(usuario);
	}

}
