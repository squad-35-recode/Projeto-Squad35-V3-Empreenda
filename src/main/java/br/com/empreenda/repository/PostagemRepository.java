package br.com.empreenda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.empreenda.entity.Perfil;
import br.com.empreenda.entity.Postagem;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {

	@Query("SELECT p, pu.fotoUrl, u.nome, u.id FROM Postagem p " +
	           "INNER JOIN p.perfil pu " +
	           "INNER JOIN pu.usuario u " +
	           "ORDER BY p.id DESC")
    List<Postagem> findAllPostagensWithUserInfo();
	
	@Query("SELECT p, pu.fotoUrl, u.nome, u.id FROM Postagem p " +
	           "INNER JOIN p.perfil pu " +
	           "INNER JOIN pu.usuario u " +
			   "WHERE p.id = :postId")
	 Postagem findAllPostagensWithUserInfoById(@Param("postId") Long postId);
	
	@Query("SELECT p, pu.fotoUrl, u.nome, u.id FROM Postagem p " +
	           "INNER JOIN p.perfil pu " +
	           "INNER JOIN pu.usuario u " +
			   "WHERE pu.id = :perfilId " +
			   "ORDER BY p.id DESC")
	 List<Postagem> findAllPostagensWithUserInfoByIdPerfil(@Param("perfilId") Long perfilId);
	
	Postagem findByPerfil(Perfil perfil);
	
	List<Postagem> findByTituloContaining(String postagem);
	
	List<Postagem> findByCategoria(String categoria);
	
}
