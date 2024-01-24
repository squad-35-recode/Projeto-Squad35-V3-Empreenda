package br.com.empreenda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.empreenda.entity.Curso;
import br.com.empreenda.entity.Perfil;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long	>{

	List<Curso> findAllByIdColaborador(Perfil perfil);
	
	@Query("SELECT c, pu.fotoUrl, u.nome, u.id FROM Curso c " +
	           "INNER JOIN c.idColaborador pu " +
	           "INNER JOIN pu.usuario u " +
	           "WHERE c.id = :cursoId")
    Curso findCursoDetails(@Param("cursoId") Long cursoId);
	
	List<Curso> findByCategoria(String categoria);
	
	List<Curso> findByTituloContaining(String titulo);
}
