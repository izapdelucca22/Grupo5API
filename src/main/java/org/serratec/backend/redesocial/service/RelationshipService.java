package org.serratec.backend.redesocial.service;

import org.serratec.backend.redesocial.model.Relationship;
import org.serratec.backend.redesocial.repository.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RelationshipService {
    @Autowired
    private RelationshipRepository relationshipRepository;

    public List<Relationship> findAll() {
        return relationshipRepository.findAll();
    }
    
    // Instancia para suportar paginacao
    public Page<Relationship> findAll(Pageable pageable) {
        return relationshipRepository.findAll(pageable);
    }


    public Optional<Relationship> findById(Long id) {
        return relationshipRepository.findById(id);
    }

    public Relationship save(Relationship relationship) {
        return relationshipRepository.save(relationship);
    }

    public void deleteById(Long id) {
        relationshipRepository.deleteById(id);
    }
}