package br.com.empreenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.empreenda.entity.LikesPostagem;
import br.com.empreenda.entity.Postagem;
import br.com.empreenda.entity.Usuario;

public interface LikesPostagemRepository extends JpaRepository<LikesPostagem, Long> {

	LikesPostagem findByUsuarioAndPostagem(Usuario usuario, Postagem postagem);
	
	int countByPostagem(Postagem postagem);
	
	int countByPostagemId(Long postId);
}
