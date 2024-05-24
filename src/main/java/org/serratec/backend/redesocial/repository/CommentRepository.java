package org.serratec.backend.redesocial.repository;

import java.util.Optional;

import org.serratec.backend.redesocial.model.Comment;
import org.serratec.backend.redesocial.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

	Page<Comment> findAll(Pageable pageable);
	
	Optional<Comment> findByIdAndPost(Long CommentId, Post post);
	
	
}
