package br.com.empreenda.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.empreenda.entity.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>{

	@Query("SELECT p FROM Perfil p WHERE p.usuario.id = :usuarioId")
	Perfil findByIdUsuarioId(@Param("usuarioId") Long usuarioId);
	
	Perfil findByUsuario_Email(String email);

	@Query("SELECT p FROM Perfil p WHERE p.usuario.id = :usuarioId")
	Optional<Perfil> findByIdUsuarioIdOpt(@Param("usuarioId") Long usuarioId);

}
