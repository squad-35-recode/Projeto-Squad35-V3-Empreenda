package br.com.empreenda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.empreenda.entity.LikesPostagem;
import br.com.empreenda.entity.Postagem;

@Repository
public interface LikesPostagemRepository extends JpaRepository<LikesPostagem, Long>{ 
	List<LikesPostagem> findByPostagem(Postagem postagem);
}
