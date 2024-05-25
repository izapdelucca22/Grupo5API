package org.serratec.backend.redesocial.service;

import java.util.Optional;

import org.serratec.backend.redesocial.model.Perfil;
import org.serratec.backend.redesocial.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PerfilService {

	@Autowired
	private PerfilRepository perfilRepository;
	
	public Perfil buscar(Long usuarioPerfilPK) {
		Optional<Perfil> perfilOpt = perfilRepository.findById(usuarioPerfilPK);
		return perfilOpt.get();
	}
	
}