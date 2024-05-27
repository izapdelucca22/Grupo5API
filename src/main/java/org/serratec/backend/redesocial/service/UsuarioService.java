package org.serratec.backend.redesocial.service;

import java.time.LocalDate;

import java.util.HashSet;

import java.util.Set;

import org.serratec.backend.redesocial.DTO.UsuarioDTO;
import org.serratec.backend.redesocial.DTO.UsuarioInserirDTO;
import org.serratec.backend.redesocial.exception.EmailException;
import org.serratec.backend.redesocial.exception.NotFoundException;
import org.serratec.backend.redesocial.exception.SenhaException;
import org.serratec.backend.redesocial.model.Perfil;
import org.serratec.backend.redesocial.model.Usuario;
import org.serratec.backend.redesocial.model.UsuarioPerfil;
import org.serratec.backend.redesocial.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

	@Autowired
	private PerfilService perfilService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	public Page<UsuarioDTO> findAll(Pageable pageable) {
		Page<Usuario> usuariosPage = usuarioRepository.findAll(pageable);
		return usuariosPage.map(UsuarioDTO::new);
	}

	public UsuarioDTO findById(Long id) throws NotFoundException {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
		return new UsuarioDTO(usuario);
	}

	@Transactional
	public UsuarioInserirDTO inserir(UsuarioInserirDTO usuarioInserirDTO) throws EmailException, SenhaException {
		if (!usuarioInserirDTO.getSenha().equals(usuarioInserirDTO.getConfirmaSenha())) {
			throw new SenhaException("Senha e Confirmação de Senha não coincidem");
		}

		Usuario usuario = new Usuario();
		usuario.setNome(usuarioInserirDTO.getNome());
		usuario.setSobrenome(usuarioInserirDTO.getSobrenome());
		usuario.setEmail(usuarioInserirDTO.getEmail());
		usuario.setSenha(encoder.encode(usuarioInserirDTO.getSenha()));
		usuario.setDataNascimento(usuarioInserirDTO.getDataNascimento());

		Set<UsuarioPerfil> usuarioPerfis = new HashSet<>();
		for (Perfil perfil : usuarioInserirDTO.getPerfis()) {
			perfil = perfilService.buscar(perfil.getId());
			usuarioPerfis.add(new UsuarioPerfil(usuario, perfil, LocalDate.now()));
		}

		usuario.setUsuarioPerfis(usuarioPerfis);

		usuario = usuarioRepository.save(usuario);

		return new UsuarioInserirDTO(usuario);
	}

	@Transactional
	public UsuarioInserirDTO atualizar(Long id, UsuarioInserirDTO usuarioInserirDTO) {
		Usuario usuarioExistente = usuarioRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Usuario não encontrado"));

		usuarioExistente.setNome(usuarioInserirDTO.getNome());
		usuarioExistente.setSobrenome(usuarioInserirDTO.getSobrenome());
		usuarioExistente.setEmail(usuarioInserirDTO.getEmail());
		usuarioExistente.setSenha(encoder.encode(usuarioInserirDTO.getSenha()));
		usuarioExistente.setDataNascimento(usuarioInserirDTO.getDataNascimento());

		Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
		return new UsuarioInserirDTO(usuarioAtualizado);
	}

	@Transactional
	public void delete(Long id) throws NotFoundException {
		if (!usuarioRepository.existsById(id)) {
			throw new NotFoundException("Usuário não encontrado");
		}
		usuarioRepository.deleteById(id);
	}

}
