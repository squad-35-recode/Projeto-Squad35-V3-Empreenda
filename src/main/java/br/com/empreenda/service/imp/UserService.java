package br.com.empreenda.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.empreenda.dto.UserDto;
import br.com.empreenda.entity.Usuario;

@Service
public interface UserService {
	
	void saveUser(UserDto userDto);
	
	void saveUserColab(UserDto userDto);
	
	Usuario findUserByEmail(String email);
	
	List<UserDto> FindAllUsers();
	
	void saveUserAdmin(UserDto userDto, Usuario usuario);
	
	void saveUserAdmin(Usuario usuario);

}
