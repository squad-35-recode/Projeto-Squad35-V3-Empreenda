package br.com.empreenda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.empreenda.entity.Curso;
import br.com.empreenda.entity.InscricaoCurso;
import br.com.empreenda.entity.Perfil;

@Repository
public interface InscricaoCursoRepository extends JpaRepository<InscricaoCurso, Long> {

	@Query("SELECT CASE WHEN COUNT(ic) > 0 THEN true ELSE false END FROM InscricaoCurso ic " +
		    "WHERE ic.perfil = :perfilId " + 
		    "AND ic.curso = :cursoId ")
	boolean existsByCursoAndPerfil(@Param("perfilId") Perfil perfil, @Param("cursoId") Curso curso);
	
	List<InscricaoCurso> findByPerfil(Perfil perfil);
	
	public int countByPerfilId(Long perfilId);
	
	@Query("SELECT ic, c.idColaborador FROM InscricaoCurso ic " +
		       "INNER JOIN ic.perfil p " +
		       "INNER JOIN ic.curso c " +
		       "WHERE c.idColaborador = :colaboradorId " +
		       "ORDER BY ic.id DESC")
	List<InscricaoCurso> findInscricoesByColaboradorId(@Param("colaboradorId") Perfil colaboradorId);
	
	InscricaoCurso findByPerfilId(Perfil perfil);


}
