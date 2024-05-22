package org.serratec.backend.redesocial.repository;

import org.serratec.backend.redesocial.model.UsuarioRelationship;
import org.serratec.backend.redesocial.model.UsuarioRelationshipPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRelationshipRepository extends JpaRepository<UsuarioRelationship, UsuarioRelationshipPK>{

}
