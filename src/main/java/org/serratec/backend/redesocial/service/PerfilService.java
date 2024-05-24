package org.serratec.backend.redesocial.service;

import java.util.Optional;

import org.serratec.backend.redesocial.model.Perfil;
import org.serratec.backend.redesocial.model.UsuarioPerfil;
import org.serratec.backend.redesocial.model.UsuarioPerfilPK;
import org.serratec.backend.redesocial.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PerfilService {

	@Autowired
	private PerfilRepository perfilRepository;
	
	public Perfil buscar(Long usuarioPerfilPK) {
		Optional<Perfil> perfilOpt = perfilRepository.findById(usuarioPerfilPK);
		return perfilOpt.get();
	}
	
	
	// DANDO MERDA, RESOLVER ISSO
	public UsuarioPerfil buscar(UsuarioPerfilPK id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}