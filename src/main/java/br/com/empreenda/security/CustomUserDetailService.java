package br.com.empreenda.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.empreenda.entity.Role;
import br.com.empreenda.entity.Usuario;
import br.com.empreenda.repository.UsuarioRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(email);
		
		if (usuario != null) {
			return new org.springframework.security.core.userdetails.User(
					usuario.getEmail(),
					usuario.getSenha(),
					mapRolesToAuthorities(usuario.getRoles())
					);
		} else {
			throw new UsernameNotFoundException("Email ou senha inválidos");
		}
	}
	
	private Collection <? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles) {
		Collection <? extends GrantedAuthority> mapRoles = roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
		return mapRoles;
	}

}
