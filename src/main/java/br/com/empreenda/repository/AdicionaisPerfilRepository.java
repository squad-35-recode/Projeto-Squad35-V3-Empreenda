package br.com.empreenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.empreenda.entity.AdicionaisPerfil;
import br.com.empreenda.entity.Perfil;

@Repository
public interface AdicionaisPerfilRepository extends JpaRepository<AdicionaisPerfil, Long>{

	AdicionaisPerfil findByPerfilId(Long id); 
	
	AdicionaisPerfil findByPerfil(Perfil perfil);
	

}
