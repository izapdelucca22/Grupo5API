package org.serratec.backend.redesocial.service;

import java.io.IOException;
import java.util.Optional;

import org.serratec.backend.redesocial.model.Foto;
import org.serratec.backend.redesocial.model.Usuario;
import org.serratec.backend.redesocial.repository.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FotoService {
	
	@Autowired
	private FotoRepository fotoRepository;
	
	public Foto inserir(Usuario usuario, MultipartFile file) throws IOException {
		Foto foto = new Foto();
		foto.setNome(file.getName());
		foto.setTipo(file.getContentType());
		foto.setDados(file.getBytes());
		foto.setUsuario(usuario);
		
		foto = fotoRepository.save(foto);
		
		return foto;
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