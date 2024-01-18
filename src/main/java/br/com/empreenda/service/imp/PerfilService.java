package br.com.empreenda.service.imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.empreenda.entity.AdicionaisPerfil;
import br.com.empreenda.entity.EnderecoPerfil;
import br.com.empreenda.entity.Perfil;
import br.com.empreenda.entity.Usuario;
import br.com.empreenda.repository.AdicionaisPerfilRepository;
import br.com.empreenda.repository.EnderecoPerfilRepository;
import br.com.empreenda.repository.PerfilRepository;
import br.com.empreenda.repository.UsuarioRepository;

@Service
public class PerfilService {

	@Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoPerfilRepository enderecoPerfilRepository;

    @Autowired
    private AdicionaisPerfilRepository adicionaisPerfilRepository;

    public Perfil buscarPerfilPorUsuarioId(Long usuarioId) {
        return perfilRepository.findByIdUsuarioId(usuarioId);
    }
    
    public Perfil buscarPerfilPorUsername(String username) {
        return perfilRepository.findByUsuario_Email(username);
    }

    public void salvarPerfil(Perfil perfil) {
        perfilRepository.save(perfil);
    }
    
    public Perfil verificarOuCriarPerfil(Long usuarioId) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            Perfil perfil = buscarPerfilPorUsuarioId(usuarioId);
            if (perfil == null) {
                perfil = new Perfil();
                perfil.setUsuario(usuario);
                salvarPerfil(perfil);

                // Verifica e cria EnderecoPerfil
                EnderecoPerfil enderecoPerfil = enderecoPerfilRepository.findByPerfilId(perfil.getId());
                if (enderecoPerfil == null) {
                    enderecoPerfil = new EnderecoPerfil();
                    // configuração do EnderecoPerfil
                    enderecoPerfilRepository.save(enderecoPerfil);
                    perfil.setEndereco(enderecoPerfil);
                    salvarPerfil(perfil);
                }

                // Verifica e cria AdicionaisPerfil
                AdicionaisPerfil adicionaisPerfil = adicionaisPerfilRepository.findByPerfilId(perfil.getId());
                if (adicionaisPerfil == null) {
                    adicionaisPerfil = new AdicionaisPerfil();
                    // configuração do AdicionaisPerfil
                    adicionaisPerfilRepository.save(adicionaisPerfil);
                    perfil.setAdicionais(adicionaisPerfil);
                    salvarPerfil(perfil);
                }
            }
            return perfil;
        } else {
            // Tratar o caso em que o usuário não é encontrado
            return null;
        }
 
    }
}
