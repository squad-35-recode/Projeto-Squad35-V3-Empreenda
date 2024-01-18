package br.com.empreenda.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.empreenda.entity.EnderecoPerfil;
import br.com.empreenda.entity.Perfil;
import br.com.empreenda.repository.EnderecoPerfilRepository;

@Service
public class EnderecoService {

	@Autowired
    private EnderecoPerfilRepository enderecoPerfilRepository;

    // Método para buscar o endereço por perfil
    public EnderecoPerfil buscarEnderecoPorPerfil(Perfil perfil) {
        return enderecoPerfilRepository.findByPerfil(perfil);
    }

    // Método para salvar ou atualizar um endereço
    public void salvarEndereco(EnderecoPerfil endereco) {
        enderecoPerfilRepository.save(endereco);
    }
}
