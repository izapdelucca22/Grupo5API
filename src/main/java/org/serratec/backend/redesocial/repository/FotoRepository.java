package org.serratec.backend.redesocial.repository;

import java.util.Optional;

import org.serratec.backend.redesocial.model.Foto;
import org.serratec.backend.redesocial.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {

	public Optional<Foto> findByUsuario(Usuario usuario);
	
}
