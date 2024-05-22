package org.serratec.backend.redesocial.controller;

import org.serratec.backend.redesocial.model.Usuario;
import org.serratec.backend.redesocial.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    
    // MOSTRA TODOS OS USUARIOS
    @GetMapping
    public List<Usuario> getAllUsers() {
        return usuarioService.findAll();
    }
    
    // MOSTRA OS USUARIOS COM PAGINACAO
    @GetMapping("/paged")
    public Page<Usuario> getAllUsers(Pageable pageable) {
        return usuarioService.findAll(pageable);
    }

    
    // MOSTRA UM USUARIO ESPECIFICO PELO ID DO MESMO
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Long id) {
        Optional<Usuario> user = usuarioService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // CRIA UM NOVO USUARIO
    @PostMapping
    public Usuario createUser(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }
    
    // DELETA UM USUARIO JA EXISTENTE PELO ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
