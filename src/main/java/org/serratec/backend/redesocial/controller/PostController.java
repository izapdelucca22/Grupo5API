package org.serratec.backend.redesocial.controller;

import org.serratec.backend.redesocial.model.Post;
import org.serratec.backend.redesocial.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;
    
    // MOSTRA TODOS OS POSTS
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.findAll();
    }
    
    // MOSTRA POSTS COM PAGINACAO
    @GetMapping("/paged")
    public Page<Post> getAllPosts(Pageable pageable) {
        return postService.findAll(pageable);
    }

    
    // MOSTRA UM POST ESPECIFICO PELO ID DO MESMO
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.findById(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // CRIA UM NOVO POST
    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.save(post);
    }
    
    // DELETA UM POST ESPECIFICO JA EXISTENTE PELO ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}