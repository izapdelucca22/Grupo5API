package org.serratec.backend.redesocial.service;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import org.serratec.backend.redesocial.model.Foto;
import org.serratec.backend.redesocial.model.Usuario;
import org.serratec.backend.redesocial.repository.FotoRepository;
import org.serratec.backend.redesocial.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Service
public class FotoService {
	
	@Autowired
	private FotoRepository fotoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Foto inserir(MultipartFile file, Long id) throws IOException {
		Optional<Usuario> byId = usuarioRepository.findById(id);
//		URI uri = ServletUriComponentsBuilder
//				.fromCurrentContextPath()
//				.path("/usuarios/{id}/foto")
//				.buildAndExpand(foto.getId())
//				.toUri();
		
		Foto ft = new Foto();
		ft.setNome(file.getName());
		ft.setTipo(file.getContentType());
		ft.setDados(file.getBytes());
		ft.setUsuario(byId.get());
//		ft.setUrl(uri.toString());
		//dto.setPerfis(usuario.getPerfis());
		
		return fotoRepository.save(ft);
	}
	
	@Transactional
	public Foto buscarPorIdUsuario(Long id) {
		Usuario usuario = new Usuario();
		usuario.setId(id);
		
		Optional<Foto> fotoOpt = fotoRepository.findByUsuario(usuario);
		
		if (fotoOpt.isEmpty()) {
			return null;
		}
		return fotoOpt.get();
	}

}