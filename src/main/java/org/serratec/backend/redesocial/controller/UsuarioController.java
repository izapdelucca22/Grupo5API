package org.serratec.backend.redesocial.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.serratec.backend.redesocial.DTO.UsuarioDTO;
import org.serratec.backend.redesocial.exception.EmailException;
import org.serratec.backend.redesocial.exception.SenhaException;
import org.serratec.backend.redesocial.model.Foto;
import org.serratec.backend.redesocial.model.Usuario;
import org.serratec.backend.redesocial.service.FotoService;
import org.serratec.backend.redesocial.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private FotoService fotoService;

//	@GetMapping("/listarDTO")
//	public ResponseEntity<List<UsuarioDTO>> listar() {
//		List<UsuarioDTO> usuarios = usuarioService.listar();
//		return ResponseEntity.ok(usuarios);
//	}
//
//	@GetMapping("/{id}/DTO")
//	public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long id) {
//		UsuarioDTO usuario = usuarioService.buscar(id);
//		return ResponseEntity.ok(usuario);
//	}

	@GetMapping("/{id}/foto")
	public ResponseEntity<byte[]> buscarFoto(@PathVariable Long id) {
		Foto foto = fotoService.buscarPorIdUsuario(id);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", foto.getTipo());
		headers.add("Content-length", String.valueOf(foto.getDados().length));

		return new ResponseEntity<>(foto.getDados(), headers, HttpStatus.OK);
	}



	//////////////////////

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
    public ResponseEntity<UsuarioDTO> createUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) throws EmailException, SenhaException {
        UsuarioDTO novoUsuario = usuarioService.inserir(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) throws NotFoundException, EmailException, SenhaException {
        UsuarioDTO usuarioAtualizado = usuarioService.inserir(usuarioDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) throws NotFoundException {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}





