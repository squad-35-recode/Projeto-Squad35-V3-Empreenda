package br.com.empreenda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.empreenda.entity.ComentariosPostagem;
import br.com.empreenda.entity.Postagem;

public interface ComentariosPostagemRepository extends JpaRepository<ComentariosPostagem, Long>{
	
	ComentariosPostagem findByPerfilId(Long id);
	
	List<ComentariosPostagem> findByPostagemId(Long id);
	
	@Query("SELECT cp, pu.fotoUrl, u.nome, u.id FROM ComentariosPostagem cp " +
	           "INNER JOIN cp.perfil pu " +
	           "INNER JOIN pu.usuario u " +
	           "ORDER BY cp.id DESC")
	List<ComentariosPostagem> findByPostagemWithUserInfo(Long id);
	
	@Query("SELECT cp, pu.fotoUrl, u.nome, u.id FROM ComentariosPostagem cp " +
	           "INNER JOIN cp.perfil pu " +
	           "INNER JOIN pu.usuario u " +
			   "WHERE cp.postagem = :postagem " + 
	           "ORDER BY cp.id DESC")
	List<ComentariosPostagem> findAllCommentsByPostWithUserInfoById(Postagem postagem);
	
	public int countByPostagemId(Long postagemId);
	
	public int countById(Long id);
}
