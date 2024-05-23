package org.serratec.backend.redesocial.repository;

import org.serratec.backend.redesocial.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	Page<Post> findAll(Pageable pageable);

	
	@Query(value = "SELECT u.nome AS nome_usuario, "+
			"DATE_PART('year', age(now()), u.data_nascimento)) AS idade " +
			"p.conteudo AS conteudo_post " +
			"INNER JOIN usuario u ON .id_usuario = u.id_usuario " +
			"WHERE DATE_PART('year', age(now()), u.data_nascimento)) =?1 "+
			"ORDER BY idade", nativeQuery = true)
	Page<Object[]> listarPostPorIdade(int idade, Pageable pageable);

}
