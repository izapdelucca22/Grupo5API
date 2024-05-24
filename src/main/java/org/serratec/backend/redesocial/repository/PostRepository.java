package org.serratec.backend.redesocial.repository;

import java.util.Optional;

import org.serratec.backend.redesocial.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	Page<Post> findAll(Pageable pageable);
	
    @Query("SELECT p FROM Post p WHERE p.id = :postId AND p.usuario.id = :usuarioId")
    Optional<Post> findByIdAndUsuarioId(Long postId, Long usuarioId);
	
	@Query(value = "SELECT u.nome AS nome_usuario, "+
            "DATE_PART('year', age(now(), u.data_nascimento)) AS idade, " +
            "p.conteudo AS conteudo_post " +
            "FROM post p " +
            "INNER JOIN usuario u ON p.id_usuario = u.id_usuario " +
            "WHERE DATE_PART('year', age(now(), u.data_nascimento)) = ?1 " +
            "ORDER BY idade", nativeQuery = true)
	Page<Object[]> listarPostPorIdade(int idade, Pageable pageable);

}
