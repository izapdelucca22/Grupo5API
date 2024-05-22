package org.serratec.backend.redesocial.repository;

import org.serratec.backend.redesocial.model.Relationship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
	Page<Relationship> findAll(Pageable pageable);
}
