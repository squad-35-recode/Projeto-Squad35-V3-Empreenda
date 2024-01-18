package br.com.empreenda.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.empreenda.entity.AdicionaisPerfil;
import br.com.empreenda.entity.Perfil;
import br.com.empreenda.repository.AdicionaisPerfilRepository;

@Service
public class AdicionaisService {

	@Autowired
    private AdicionaisPerfilRepository adicionaisPerfilRepository;

    // Método para buscar os adicionais por perfil
    public AdicionaisPerfil buscarAdicionaisPorPerfil(Perfil perfil) {
        return adicionaisPerfilRepository.findByPerfil(perfil);
    }

    // Método para salvar ou atualizar os adicionais
    public void salvarAdicionais(AdicionaisPerfil adicionais) {
        adicionaisPerfilRepository.save(adicionais);
    }

}
