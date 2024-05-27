package org.serratec.backend.redesocial.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.serratec.backend.redesocial.DTO.UsuarioRelationshipDTO;
import org.serratec.backend.redesocial.exception.NotFoundException;
import org.serratec.backend.redesocial.exception.UnauthorizedException;
import org.serratec.backend.redesocial.model.Relationship;
import org.serratec.backend.redesocial.model.Usuario;
import org.serratec.backend.redesocial.repository.RelationshipRepository;
import org.serratec.backend.redesocial.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Service
public class RelationshipService {
	@Autowired
	private RelationshipRepository relationshipRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	UsuarioDetailsImp detailsImp;
	
    @PersistenceContext
    private EntityManager entityManager;

	// Método para seguir um usuário
	@Transactional
	public void seguirUsuario(Long followedId) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails details = detailsImp.loadUserByUsername(username);
		if (!(details instanceof Usuario)) {
			throw new UnauthorizedException("Usuário não encontrado");
		}
	    Usuario usuarioAutenticado = (Usuario) details;
		
		Usuario followed = usuarioRepository.findById(followedId)
				.orElseThrow(() -> new NotFoundException("Usuário seguido não encontrado"));

		Relationship relationship = new Relationship();
		relationship.setFollower(usuarioAutenticado);
		relationship.setFollowed(followed);
		relationship.setDataInicioSeguimento(LocalDateTime.now());

		relationshipRepository.save(relationship);

	}

	// Método para deixar de seguir um usuário
	@Transactional
	public void deixarDeSeguirUsuario(Long followedId) throws NotFoundException {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails details = detailsImp.loadUserByUsername(username);
		if (!(details instanceof Usuario)) {
			throw new UnauthorizedException("Usuário não encontrado");
		}
	    Usuario usuarioAutenticado = (Usuario) details;
		
		Usuario followed = usuarioRepository.findById(followedId)
				.orElseThrow(() -> new NotFoundException("Usuário seguido não encontrado"));
		
		Usuario follower = usuarioRepository.findById(usuarioAutenticado.getId())
				.orElseThrow(() -> new NotFoundException("Usuário seguido não encontrado"));

		Relationship relationship = relationshipRepository.findByFollowerAndFollowed(follower, followed)
				.orElseThrow(() -> new NotFoundException("Relacionamento não encontrado"));
		
		relationshipRepository.deleteByFollowerAndFollowed(follower, followed);
		System.out.println("Relacionamento deletado: " + relationship.getId());
	}

	// Método para listar quem o usuario segue
	public List<UsuarioRelationshipDTO> seguindo() {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserDetails details = detailsImp.loadUserByUsername(username);
		if (!(details instanceof Usuario)) {
			throw new UnauthorizedException("Usuário não encontrado");
		}

	       Usuario usuarioAutenticado = (Usuario) details;
	        Long idUsuario = usuarioAutenticado.getId();
		
		Usuario usuario = usuarioRepository.findById(idUsuario)
				.orElseThrow(() -> new NotFoundException("Usuário não encontrado com o ID: " + idUsuario));

		List<UsuarioRelationshipDTO> usuariosSeguidosDTO = new ArrayList<>();

		for (Relationship relationship : usuario.getFollowers()) {
			Usuario usuarioSeguido = relationship.getFollowed();
			usuariosSeguidosDTO.add(new UsuarioRelationshipDTO(usuarioSeguido.getId(), usuarioSeguido.getNome(),
					usuarioSeguido.getSobrenome()));
		}

		return usuariosSeguidosDTO;
	}

	// Método para ver quem segue o usuario
	public List<UsuarioRelationshipDTO> seguidores() {
        
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails details = detailsImp.loadUserByUsername(username);

       
        if (!(details instanceof Usuario)) {
            throw new UnauthorizedException("Usuário não encontrado");
        }

        
        Usuario usuarioAutenticado = (Usuario) details;
        Long idUsuario = usuarioAutenticado.getId();

        
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com o ID: " + idUsuario));

        List<UsuarioRelationshipDTO> seguidoresDTO = new ArrayList<>();

        for (Relationship relationship : usuario.getFollowings()) {
            Usuario seguidor = relationship.getFollower();
            seguidoresDTO.add(new UsuarioRelationshipDTO(seguidor.getId(), seguidor.getNome(), seguidor.getSobrenome()));
        }

        return seguidoresDTO;
    }
	
}
