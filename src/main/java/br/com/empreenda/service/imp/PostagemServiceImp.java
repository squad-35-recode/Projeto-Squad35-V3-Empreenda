package br.com.empreenda.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.empreenda.entity.Postagem;
import br.com.empreenda.repository.PerfilRepository;
import br.com.empreenda.repository.PostagemRepository;
import br.com.empreenda.repository.UsuarioRepository;

@Service
public class PostagemServiceImp implements PostagemService{

	private final PostagemRepository postagemRepository;
	@SuppressWarnings("unused")
	private final UsuarioRepository usuarioRepository;
	@SuppressWarnings("unused")
	private final PerfilRepository perfilRepository;

    public PostagemServiceImp(PostagemRepository postagemRepository, UsuarioRepository usuarioRepository,
    		PerfilRepository perfilRepository) {
        this.postagemRepository = postagemRepository;
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
    }

    @Override
    public Postagem salvar(Postagem postagem) {
        return postagemRepository.save(postagem);
    }
    
    public List<Postagem> listarTodas() {
        List<Postagem> postagens = postagemRepository.findAllPostagensWithUserInfo();

        return postagens;
    }
    
}
