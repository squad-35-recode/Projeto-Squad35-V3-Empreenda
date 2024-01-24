package br.com.empreenda.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.empreenda.dto.UserDto;
import br.com.empreenda.entity.LikesPostagem;
import br.com.empreenda.entity.Postagem;
import br.com.empreenda.entity.Usuario;
import br.com.empreenda.repository.ComentariosPostagemRepository;
import br.com.empreenda.repository.LikesPostagemRepository;
import br.com.empreenda.repository.PostagemRepository;
import br.com.empreenda.repository.UsuarioRepository;
import br.com.empreenda.service.imp.PostagemServiceImp;
import br.com.empreenda.service.imp.UserService;
import br.com.empreenda.service.imp.UsuarioService;
import jakarta.validation.Valid;

@Controller
public class AuthController {

	@Autowired
	private UserService userService;
	
	@SuppressWarnings("unused")
	@Autowired
	private PostagemServiceImp postagemServiceimp;
	
	@Autowired
	private PostagemRepository postagemRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@SuppressWarnings("unused")
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ComentariosPostagemRepository comentariosPostagemRepository;
	
	@Autowired
	private LikesPostagemRepository likesPostagemRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	public AuthController(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/index")
	public String home(Model model, Principal principal) {
	    List<Postagem> postagens = postagemRepository.findAllPostagensWithUserInfo();
	    List<Integer> contagemComentarios = new ArrayList<>();
	    List<Boolean> postagensCurtidas = new ArrayList<>();

	    if (principal != null) {
	        String username = principal.getName();
	        Usuario usuario = usuarioRepository.findByEmail(username);

	        for (Postagem postagem : postagens) {
	            int numeroComentarios = comentariosPostagemRepository.countByPostagemId(postagem.getId());
	            int numeroLikes = likesPostagemRepository.countByPostagemId(postagem.getId());
	            contagemComentarios.add(numeroComentarios);

	            LikesPostagem existente = likesPostagemRepository.findByUsuarioAndPostagem(usuario, postagem);
	            postagensCurtidas.add(existente != null);
	        }
	    } else {
	        for (Postagem postagem : postagens) {
	            int numeroComentarios = comentariosPostagemRepository.countByPostagemId(postagem.getId());
	            contagemComentarios.add(numeroComentarios);
	            postagensCurtidas.add(false);
	        }
	    }

	    model.addAttribute("postagens", postagens);
	    model.addAttribute("contagemComentarios", contagemComentarios);
	    model.addAttribute("postagensCurtidas", postagensCurtidas);

	    return "index";
	}

	
	@GetMapping("/")
	public String homeRoot(Model model, Principal principal) {
		List<Postagem> postagens = postagemRepository.findAllPostagensWithUserInfo();
	    List<Integer> contagemComentarios = new ArrayList<>();
	    List<Boolean> postagensCurtidas = new ArrayList<>();

	    if (principal != null) {
	        String username = principal.getName();
	        Usuario usuario = usuarioRepository.findByEmail(username);

	        for (Postagem postagem : postagens) {
	            int numeroComentarios = comentariosPostagemRepository.countByPostagemId(postagem.getId());
	            int numeroLikes = likesPostagemRepository.countByPostagemId(postagem.getId());
	            contagemComentarios.add(numeroComentarios);

	            LikesPostagem existente = likesPostagemRepository.findByUsuarioAndPostagem(usuario, postagem);
	            postagensCurtidas.add(existente != null);
	        }
	    } else {
	        for (Postagem postagem : postagens) {
	            int numeroComentarios = comentariosPostagemRepository.countByPostagemId(postagem.getId());
	            contagemComentarios.add(numeroComentarios);
	            postagensCurtidas.add(false);
	        }
	    }

	    model.addAttribute("postagens", postagens);
	    model.addAttribute("contagemComentarios", contagemComentarios);
	    model.addAttribute("postagensCurtidas", postagensCurtidas);

	    return "index";
	}
	
	@GetMapping("/index/filtrar")
	public String homeFiltrada(@RequestParam("categoria")String categoria,Model model, Principal principal) {
		List<Postagem> postagens = postagemRepository.findByCategoria(categoria);
	    List<Integer> contagemComentarios = new ArrayList<>();
	    List<Boolean> postagensCurtidas = new ArrayList<>();

	    if (principal != null) {
	        String username = principal.getName();
	        Usuario usuario = usuarioRepository.findByEmail(username);

	        for (Postagem postagem : postagens) {
	            int numeroComentarios = comentariosPostagemRepository.countByPostagemId(postagem.getId());
	            int numeroLikes = likesPostagemRepository.countByPostagemId(postagem.getId());
	            contagemComentarios.add(numeroComentarios);

	            LikesPostagem existente = likesPostagemRepository.findByUsuarioAndPostagem(usuario, postagem);
	            postagensCurtidas.add(existente != null);
	        }
	    } else {
	        for (Postagem postagem : postagens) {
	            int numeroComentarios = comentariosPostagemRepository.countByPostagemId(postagem.getId());
	            contagemComentarios.add(numeroComentarios);
	            postagensCurtidas.add(false);
	        }
	    }

	    model.addAttribute("postagens", postagens);
	    model.addAttribute("contagemComentarios", contagemComentarios);
	    model.addAttribute("postagensCurtidas", postagensCurtidas);

	    return "index";
	}
	
	@GetMapping("/login")
	public String showLoginForm(Model model) {
		UserDto usuario = new UserDto();
		model.addAttribute("usuario", usuario);
		return "login";
	}
	
	@GetMapping("/register")
	public String showRegistrarionForm(Model model) {
		UserDto usuario = new UserDto();
		model.addAttribute("usuario", usuario);
		return "cadastro";
	}
	
	
	@PostMapping("/register/save")
	public String registration(@Valid @ModelAttribute("usuario") UserDto userDto, BindingResult result, Model model) {
		Usuario usuarioExist = userService.findUserByEmail(userDto.getEmail());
		
		if(usuarioExist != null && usuarioExist.getEmail() != null && !usuarioExist.getEmail().isEmpty()) {
			result.rejectValue("email", null, "Já existe uma conta cadastrada com esse E-mail");
		}
		
		if (result.hasErrors()) {
			model.addAttribute("usuario", userDto);
			return "redirect:/register";
		}
		
		userService.saveUser(userDto);
		return "redirect:/register?success";
	}
	
	@PostMapping("/register/colab/save")
	public String registrationColab(@Valid @ModelAttribute("usuario") UserDto userDto, BindingResult result, Model model, @RequestParam("origem") String origem) {

		String origemForm = origem;
		
		Usuario usuarioExist = userService.findUserByEmail(userDto.getEmail());

	    if (usuarioExist != null && usuarioExist.getEmail() != null && !usuarioExist.getEmail().isEmpty()) {
	        result.rejectValue("email", null, "Já existe uma conta cadastrada com esse E-mail");
	    }

	    if (result.hasErrors()) {
	        model.addAttribute("usuario", userDto);
	        if ("/cadastro".equals(origemForm)) {
		        return "redirect:/register?errorInput"; 
		    } else {
		        return "redirect:/login?errorInput"; 
		    }
	    }

	    userService.saveUserColab(userDto);

	    if ("/cadastro".equals(origemForm)) {
	        return "redirect:/register?successColab"; 
	    } else {
	        return "redirect:/login?successColab"; 
	    }
	}

	
}
