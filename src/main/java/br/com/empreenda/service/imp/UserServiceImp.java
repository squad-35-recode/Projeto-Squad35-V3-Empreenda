package br.com.empreenda.service.imp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.empreenda.dto.UserDto;
import br.com.empreenda.entity.Role;
import br.com.empreenda.entity.Usuario;
import br.com.empreenda.repository.RoleRepository;
import br.com.empreenda.repository.UsuarioRepository;

@Service
public class UserServiceImp implements UserService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void saveUser(UserDto userDto) {
		Usuario usuario = new Usuario();
		usuario.setNome(userDto.getNome());
		usuario.setEmail(userDto.getEmail());
		usuario.setSenha(passwordEncoder.encode(userDto.getSenha()));
		
		Role role = roleRepository.findByName("ROLE_USER");
		if(role == null) {
			role = checkRoleExist();
		}
		usuario.setRoles(Arrays.asList(role));
		usuarioRepository.save(usuario);
	}
	
	@Override
	public void saveUserColab(UserDto userDto) {
		Usuario usuario = new Usuario();
		usuario.setNome(userDto.getNome());
		usuario.setEmail(userDto.getEmail());
		usuario.setSenha(passwordEncoder.encode(userDto.getSenha()));
		
		Role role = roleRepository.findByName("ROLE_COLAB");
		if(role == null) {
			role = checkRoleColabExist();
		}
		usuario.setRoles(Arrays.asList(role));
		usuarioRepository.save(usuario);
	}
	
	@Override
	public Usuario findUserByEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}
	
	public List<UserDto> findAllUsers(){
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarios.stream()
				.map((usuario) -> mapToUserDto(usuario))
				.collect(Collectors.toList());
	}
	
	private UserDto mapToUserDto(Usuario usuario) {
		UserDto userDto = new UserDto();
		userDto.setId(usuario.getId());
		userDto.setNome(usuario.getNome());
		userDto.setEmail(usuario.getEmail());
		return userDto;
	}
	
	
	private Role checkRoleExist() {
		Role role = new Role();
		role.setName("ROLE_USER");
		return roleRepository.save(role);
	}
	
	private Role checkRoleColabExist() {
	    Role roleColab = roleRepository.findByName("ROLE_COLAB");
	    
	    if (roleColab == null) {
	        roleColab = new Role();
	        roleColab.setName("ROLE_COLAB");
	        roleColab = roleRepository.save(roleColab);
	    }
	    
	    return roleColab;
	}


	@Override
	public List<UserDto> FindAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveUserAdmin(UserDto userDto, Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveUserAdmin(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}
}
