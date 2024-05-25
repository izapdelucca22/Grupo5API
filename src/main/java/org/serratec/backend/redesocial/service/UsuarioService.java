package org.serratec.backend.redesocial.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;

import java.util.Set;

import org.serratec.backend.redesocial.DTO.UsuarioDTO;
import org.serratec.backend.redesocial.DTO.UsuarioInserirDTO;
import org.serratec.backend.redesocial.DTO.UsuarioRelationshipDTO;
import org.serratec.backend.redesocial.exception.EmailException;
import org.serratec.backend.redesocial.exception.NotFoundException;
import org.serratec.backend.redesocial.exception.SenhaException;
import org.serratec.backend.redesocial.model.Perfil;
import org.serratec.backend.redesocial.model.Relationship;
import org.serratec.backend.redesocial.model.Usuario;
import org.serratec.backend.redesocial.model.UsuarioPerfil;
import org.serratec.backend.redesocial.repository.RelationshipRepository;
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
	private RelationshipRepository relationshipRepository;

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

	// Método para seguir um usuário
	@Transactional
	public void seguirUsuario(Long usuarioId, Long followedId) {
		Usuario follower = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new NotFoundException("Usuário seguidor não encontrado"));
		Usuario followed = usuarioRepository.findById(followedId)
				.orElseThrow(() -> new NotFoundException("Usuário seguido não encontrado"));

		Relationship relationship = new Relationship();
		relationship.setFollower(follower);
		relationship.setFollowed(followed);
		relationship.setDataInicioSeguimento(LocalDateTime.now());

		relationshipRepository.save(relationship);

	}

	// Método para deixar de seguir um usuário
	@Transactional
	public void deixarDeSeguirUsuario(Long usuarioId, Long followedId) throws NotFoundException {
		Usuario follower = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new NotFoundException("Usuário seguidor não encontrado"));
		Usuario followed = usuarioRepository.findById(followedId)
				.orElseThrow(() -> new NotFoundException("Usuário seguido não encontrado"));

		Relationship relationship = relationshipRepository.findByFollowerAndFollowed(follower, followed)
				.orElseThrow(() -> new NotFoundException("Relacionamento não encontrado"));

		relationshipRepository.delete(relationship);
	}

	public List<UsuarioRelationshipDTO> seguindo(Long idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario)
				.orElseThrow(() -> new NotFoundException("Usuário não encontrado com o ID: " + idUsuario));

		List<UsuarioRelationshipDTO> usuariosSeguidosDTO = new ArrayList<>();

		for (Relationship relationship : usuario.getFollowings()) {
			Usuario usuarioSeguido = relationship.getFollower();
			usuariosSeguidosDTO.add(new UsuarioRelationshipDTO(usuarioSeguido.getId(), usuarioSeguido.getNome(),
					usuarioSeguido.getSobrenome()));
		}

		return usuariosSeguidosDTO;
	}

	public List<UsuarioRelationshipDTO> seguidores(Long idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario)
				.orElseThrow(() -> new NotFoundException("Usuário não encontrado com o ID: " + idUsuario));

		List<UsuarioRelationshipDTO> seguidoresDTO = new ArrayList<>();

		for (Relationship relationship : usuario.getFollowings()) {
			Usuario seguidor = relationship.getFollower();
			seguidoresDTO
					.add(new UsuarioRelationshipDTO(seguidor.getId(), seguidor.getNome(), seguidor.getSobrenome()));
		}

		return seguidoresDTO;
	}

}
