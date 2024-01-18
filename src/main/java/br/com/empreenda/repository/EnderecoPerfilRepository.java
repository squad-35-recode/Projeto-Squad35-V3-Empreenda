package br.com.empreenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.empreenda.entity.EnderecoPerfil;
import br.com.empreenda.entity.Perfil;

@Repository
public interface EnderecoPerfilRepository extends JpaRepository<EnderecoPerfil, Long>{

	EnderecoPerfil findByPerfilId(Long id);
	
	EnderecoPerfil findByPerfil(Perfil perfil);

}
