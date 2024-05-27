package org.serratec.backend.redesocial.controller;

import org.serratec.backend.redesocial.DTO.UsuarioDTO;
import org.serratec.backend.redesocial.DTO.UsuarioInserirDTO;
import org.serratec.backend.redesocial.exception.EmailException;
import org.serratec.backend.redesocial.exception.SenhaException;
import org.serratec.backend.redesocial.model.Foto;
import org.serratec.backend.redesocial.service.FotoService;
import org.serratec.backend.redesocial.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private FotoService fotoService;

	@GetMapping("/{id}/foto")
	public ResponseEntity<byte[]> buscarFoto(@PathVariable Long id) {
		Foto foto = fotoService.buscarPorIdUsuario(id);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", foto.getTipo());
		headers.add("Content-length", String.valueOf(foto.getDados().length));

		return new ResponseEntity<>(foto.getDados(), headers, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Page<UsuarioDTO>> getAllUsuarios(Pageable pageable) {
		Page<UsuarioDTO> usuarios = usuarioService.findAll(pageable);
		return ResponseEntity.ok(usuarios);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) throws NotFoundException {
		UsuarioDTO usuario = usuarioService.findById(id);
		return ResponseEntity.ok(usuario);
	}

	@PostMapping
	public ResponseEntity<UsuarioInserirDTO> createUsuario(@Valid @RequestBody UsuarioInserirDTO usuarioInserirDTO)
			throws EmailException, SenhaException {
		UsuarioInserirDTO novoUsuario = usuarioService.inserir(usuarioInserirDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UsuarioInserirDTO> updateUsuario(@PathVariable Long id,
			@Valid @RequestBody UsuarioInserirDTO usuarioInserirDTO)
			throws NotFoundException, EmailException, SenhaException {
		UsuarioInserirDTO usuarioAtualizado = usuarioService.atualizar(id, usuarioInserirDTO);
		return ResponseEntity.ok(usuarioAtualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) throws NotFoundException {
		usuarioService.delete(id);
		return ResponseEntity.noContent().build();
	}
}