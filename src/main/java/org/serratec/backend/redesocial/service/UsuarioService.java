package org.serratec.backend.redesocial.service;

import org.serratec.backend.redesocial.DTO.UsuarioDTO;
import org.serratec.backend.redesocial.model.Usuario;
import org.serratec.backend.redesocial.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

	@Autowired
	private FotoService fotoService;
	
	public List<UsuarioDTO> listar() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		
		List<UsuarioDTO> usuariosDTO = new ArrayList<>();
		
		usuarios.forEach(f -> {
			usuariosDTO.add(adicionarImagemUrl(f));
		});
		
		return usuariosDTO;
	}
	
	public UsuarioDTO buscar(Long id) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
		return adicionarImagemUrl(usuarioOpt.get());
	}
	
	public UsuarioDTO inserir(Usuario usuario, MultipartFile file) throws IOException {
		usuario = usuarioRepository.save(usuario);
		fotoService.inserir(usuario, file);
		return adicionarImagemUrl(usuario);
	}
	
	
	public UsuarioDTO adicionarImagemUrl(Usuario usuario) {
		URI uri = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/usuarios/{id}/foto")
				.buildAndExpand(usuario.getId())
				.toUri();
		
		UsuarioDTO dto = new UsuarioDTO();
		dto.setNome(usuario.getNome());
		dto.setEmail(usuario.getEmail());
		dto.setUrl(uri.toString());
		dto.setDataNascimento(usuario.getDataNascimento());
		//dto.setPerfis(usuario.getPerfis());
		
		return dto;
	}
	
	//////////////////////
    
    
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
    
    // Instancia para suportar paginacao
    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }


    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteById(Long id) {
    	usuarioRepository.deleteById(id);
    }
    
    public Usuario update(Usuario usuario) {
    	return usuarioRepository.save(usuario);
    }

}
