package org.serratec.backend.redesocial.controller;

import org.serratec.backend.redesocial.model.Relationship;
import org.serratec.backend.redesocial.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/relationships")
public class RelationshipController {
    @Autowired
    private RelationshipService relationshipService;

    @GetMapping
    public List<Relationship> getAllRelationships() {
        return relationshipService.findAll();
    }
    
    // SUPORTE PARA PAGINACAO
    @GetMapping("/paged")
    public Page<Relationship> getAllRelationships(Pageable pageable) {
        return relationshipService.findAll(pageable);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Relationship> getRelationshipById(@PathVariable Long id) {
        Optional<Relationship> relationship = relationshipService.findById(id);
        return relationship.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Relationship createRelationship(@RequestBody Relationship relationship) {
        return relationshipService.save(relationship);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelationship(@PathVariable Long id) {
        relationshipService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
