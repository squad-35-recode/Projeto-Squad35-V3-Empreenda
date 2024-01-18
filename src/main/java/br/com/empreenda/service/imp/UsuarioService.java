package br.com.empreenda.service.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.empreenda.entity.Usuario;
import br.com.empreenda.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		this.usuarioRepository = usuarioRepository;
	}
	
	public List<Usuario> buscarTodosUsuarios(){
		return usuarioRepository.findAll();
	}
	
	public Usuario obterUsuarioPorId(Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		return usuario.get();
	}
	
	public Usuario createUsuario(Usuario usuario) {
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}
	
	public Usuario findByEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}
	
	@Transactional
	public void excluirUsuario(Long usuarioId) {
		
	}

	public Usuario buscarUsuarioPorEmail(String email) {
		return null;
	}
	
}
