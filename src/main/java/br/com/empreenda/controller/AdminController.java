package br.com.empreenda.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.empreenda.dto.UserDto;
import br.com.empreenda.entity.AdicionaisPerfil;
import br.com.empreenda.entity.Curso;
import br.com.empreenda.entity.EnderecoPerfil;
import br.com.empreenda.entity.InscricaoCurso;
import br.com.empreenda.entity.Perfil;
import br.com.empreenda.entity.Postagem;
import br.com.empreenda.entity.Suporte;
import br.com.empreenda.entity.Usuario;
import br.com.empreenda.repository.AdicionaisPerfilRepository;
import br.com.empreenda.repository.ComentariosPostagemRepository;
import br.com.empreenda.repository.CursoRepository;
import br.com.empreenda.repository.EnderecoPerfilRepository;
import br.com.empreenda.repository.InscricaoCursoRepository;
import br.com.empreenda.repository.PerfilRepository;
import br.com.empreenda.repository.PostagemRepository;
import br.com.empreenda.repository.SuporteRepository;
import br.com.empreenda.repository.UsuarioRepository;
import br.com.empreenda.service.imp.AdicionaisService;
import br.com.empreenda.service.imp.EnderecoService;
import br.com.empreenda.service.imp.PerfilService;
import br.com.empreenda.service.imp.PostagemService;
import br.com.empreenda.service.imp.UserService;
import br.com.empreenda.service.imp.UsuarioService;
import jakarta.validation.Valid;

@Controller
public class AdminController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PerfilRepository perfilRepository;

	@Autowired
	private PostagemRepository postagemRepository;

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private InscricaoCursoRepository inscricaoCursoRepository;
	
	@Autowired
	private AdicionaisPerfilRepository adicionaisPerfilRepository;
	
	@Autowired
	private EnderecoPerfilRepository enderecoPerfilRepository;
	
	@Autowired
	private SuporteRepository suporteRepository;

	private UsuarioService usuarioService;

	private EnderecoService enderecoService;

	private AdicionaisService adicionaisService;

	private UserService userService;

	private PerfilService perfilService;
	
	private final PostagemService postagemService;
	
	private final PasswordEncoder passwordEncoder;

	public AdminController(UsuarioService usuarioService, EnderecoService enderecoService,
			AdicionaisService adicionaisService, UserService userService, PerfilService perfilService, PasswordEncoder passwordEncoder, PostagemService postagemService) {
		this.usuarioService = usuarioService;
		this.enderecoService = enderecoService;
		this.adicionaisService = adicionaisService;
		this.userService = userService;
		this.perfilService = perfilService;
		this.passwordEncoder = passwordEncoder;
		this.postagemService = postagemService;
	}

	@GetMapping("/admin/index")
	public String index() {
		return "/admin/dashboardAdmin.html";
	}

	@GetMapping("/admin/usuario/lista")
	public String listaUsuarios(Model model) {
		List<Usuario> usuarios = usuarioRepository.findAll();

		model.addAttribute("usuarios", usuarios);

		return "admin/usuario/listaUsuarios.html";
	}

	@GetMapping("/admin/usuario/save")
	public String formCadastroUsuario(Model model) {
		UserDto usuario = new UserDto();
		model.addAttribute("usuario", usuario);
		return "admin/usuario/cadastroUsuario.html";
	}

	@PostMapping("/admin/usuario/save")
	public String registrationUser(@Valid @ModelAttribute("usuario") UserDto userDto,
			BindingResult result,
			Model model,
			@RequestParam(name = "submitBtn", required = false) String submitBtn) {

		Usuario usuarioExist = userService.findUserByEmail(userDto.getEmail());

		if (usuarioExist != null && usuarioExist.getEmail() != null && !usuarioExist.getEmail().isEmpty()) {
			result.rejectValue("email", null, "Já existe uma conta cadastrada com esse E-mail");
		}

		if (result.hasErrors()) {
			model.addAttribute("usuario", userDto);
			return "redirect:/admin/usuario/save?errorSave";
		}

		if ("comum".equals(submitBtn)) {
	        userService.saveUser(userDto);
	    } else if ("colaborador".equals(submitBtn)) {
	        userService.saveUserColab(userDto);
	    }
		return "redirect:/admin/usuario/save?successSave";
	}

	@GetMapping("/admin/usuario/editar")
	public String detalheUsuario(@RequestParam("id") Long id, Model model) {
		if (id != null) {
			Usuario usuario = usuarioRepository.getReferenceById(id);

			if (usuario != null) {
				Long idUsuario = usuario.getId();

				Perfil perfil = perfilService.buscarPerfilPorUsuarioId(idUsuario);
				if (perfil == null) {
					perfil = new Perfil();

					perfil.setFotoUrl("https://i.postimg.cc/T3G5j2KW/user-standard.png");
					perfil.setUsuario(usuario);
					perfilService.salvarPerfil(perfil);
				}

				EnderecoPerfil endereco = enderecoService.buscarEnderecoPorPerfil(perfil);
				if (endereco == null) {
					endereco = new EnderecoPerfil();
					endereco.setPerfil(perfil);
					enderecoService.salvarEndereco(endereco);
				}

				AdicionaisPerfil informacoesAdicionais = adicionaisService.buscarAdicionaisPorPerfil(perfil);
				if (informacoesAdicionais == null) {
					informacoesAdicionais = new AdicionaisPerfil();
					informacoesAdicionais.setPerfil(perfil);
					adicionaisService.salvarAdicionais(informacoesAdicionais);
				}

				Long perfilId = perfil.getId();

				model.addAttribute("adicionais", informacoesAdicionais);
				model.addAttribute("endereco", endereco);
				model.addAttribute("perfil", perfil);
				model.addAttribute("usuario", usuario);
			}
		}

		return "/admin/usuario/editarUsuario.html";
	}
	
	@PostMapping("/admin/usuario/editar/perfil")
	public ModelAndView editarPerfilAdm(@RequestParam("usuarioId")Long usuarioId, @RequestParam("perfilId") Long perfilId, Perfil perfil, EnderecoPerfil endereco, AdicionaisPerfil adicionais, Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/usuario/editar?id=" + usuarioId + "&editSuccess");
		
		Optional<Usuario> usuarioExistenteOpt = usuarioRepository.findById(usuarioId);
		Usuario usuarioExistente = usuarioExistenteOpt.get();
		
		usuarioExistente.setNome(usuario.getNome());
		
		Optional<Perfil> perfilExistenteOpt = Optional.of(perfilRepository.findByIdUsuarioId(usuarioId));
		Perfil perfilExistente = perfilExistenteOpt.get();
		
		perfilExistente.setFotoUrl(perfil.getFotoUrl());
		perfilExistente.setOcupacao(perfil.getOcupacao());
		perfilExistente.setBio(perfil.getBio());
		
		perfilExistente.setTelefone1(perfil.getTelefone1());
		perfilExistente.setTelefone2(perfil.getTelefone2());
		
		perfilExistente.setDataNas(perfil.getDataNas());
		perfilExistente.setEscolaridade(perfil.getEscolaridade());
		perfilExistente.setRenda(perfil.getRenda());
		
		Optional<AdicionaisPerfil> adicionaisExistentesOpt = Optional.of(adicionaisPerfilRepository.findByPerfilId(perfilId));
		AdicionaisPerfil adicionaisExistentes = adicionaisExistentesOpt.get();
		
		adicionaisExistentes.setContato1(adicionais.getContato1());
		adicionaisExistentes.setContato2(adicionais.getContato2());
		adicionaisExistentes.setContato3(adicionais.getContato3());
		adicionaisExistentes.setContato4(adicionais.getContato4());
		
		adicionaisExistentes.setInteresse1(adicionais.getInteresse1());
		adicionaisExistentes.setInteresse2(adicionais.getInteresse2());
		adicionaisExistentes.setInteresse3(adicionais.getInteresse3());
		
		Optional<EnderecoPerfil> enderecoExistenteOpt = Optional.of(enderecoPerfilRepository.findByPerfilId(perfilId));
		EnderecoPerfil enderecoExistente = enderecoExistenteOpt.get();
		
		enderecoExistente.setLogradouro(endereco.getLogradouro());
		enderecoExistente.setCep(endereco.getCep());
		enderecoExistente.setCidade(endereco.getCidade());
		enderecoExistente.setUf(endereco.getUf());
		
		usuarioRepository.save(usuarioExistente);
		perfilRepository.save(perfilExistente);
		enderecoPerfilRepository.save(enderecoExistente);
		adicionaisPerfilRepository.save(adicionaisExistentes);
		
		return modelAndView;
	}
	
	@PostMapping("/admin/usuario/editar/senha")
	public ModelAndView atualizarSenhaAdm(@RequestParam("idUsuario")Long usuarioId, @RequestParam("perfilId") Long perfilId, Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/usuario/editar?id=" + perfilId + "&editSuccess");
		
		Optional<Usuario> usuarioExistenteOpt = usuarioRepository.findById(usuarioId);
		Usuario usuarioExistente = usuarioExistenteOpt.get();
		
		usuarioExistente.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuarioRepository.save(usuarioExistente);
		
		return modelAndView;
	}
	
	
	/*
	 * @GetMapping("/admin/usuario/deletar") public String
	 * deletarUsuario(@RequestParam("id") Long id, Model model) { Usuario usuario =
	 * usuarioRepository.getReferenceById(id); Perfil perfil =
	 * perfilRepository.findByIdUsuarioId(id); Long perfilId = perfil.getId();
	 * 
	 * 
	 * Postagem postagem = postagemRepository.findByPerfil(perfil); if (postagem !=
	 * null) { postagemRepository.delete(postagem); }
	 * 
	 * EnderecoPerfil endereco = enderecoPerfilRepository.findByPerfil(perfil); if
	 * (endereco != null) { enderecoPerfilRepository.delete(endereco); }
	 * 
	 * AdicionaisPerfil adicionais =
	 * adicionaisPerfilRepository.findByPerfil(perfil); if (adicionais != null) {
	 * adicionaisPerfilRepository.delete(adicionais); }
	 * 
	 * InscricaoCurso inscricao = inscricaoCursoRepository.findByPerfilId(perfil);
	 * if (inscricao != null) { inscricaoCursoRepository.delete(inscricao); }
	 * 
	 * ComentariosPostagem comentarios =
	 * comentariosPostagemRepository.findByPerfilId(perfilId); if (comentarios !=
	 * null) { comentariosPostagemRepository.delete(comentarios); }
	 * 
	 * perfilRepository.delete(perfil); usuarioRepository.delete(usuario);
	 * 
	 * return "redirect:/admin/usuario/lista?deleteSuccess"; }
	 */

	
	
	

	@GetMapping("/admin/postagem/lista")
	public String listaPostagens(Model model) {
		List<Postagem> postagens = postagemRepository.findAll();

		model.addAttribute("postagens", postagens);

		return "admin/postagem/listaPostagens.html";
	}
	
	@GetMapping("/admin/postagem/detalhe")
	public String detalhePostagem(@RequestParam("id") Long id, Model model) {
		Postagem postagem = postagemRepository.getReferenceById(id);
		
		model.addAttribute("postagem",postagem);
		return "admin/postagem/detalhePostagem.html";
	}
	
	@GetMapping("/admin/postagem/deletar")
	public String deletarPostagem(@RequestParam("id") Long id) {
		Postagem postagem = postagemRepository.getReferenceById(id);
		
		postagemRepository.delete(postagem);
		
		return "redirect:/admin/postagem/lista?deleteSuccess";
	}
	
	@GetMapping("/admin/postagem/criar")
	public String criarPostagem() {
		
		return "/admin/postagem/cadastroPostagem.html";
	}
	
	@PostMapping("/admin/postagem/save")
	public String criarPostagem(Principal principal,
            @RequestParam("titulo") String titulo,
            @RequestParam("corpo") String corpo,
            @RequestParam("categoria") String categoria,
            @RequestParam("media_url") String mediaUrl) {

	String username = principal.getName();
	Usuario usuario = usuarioRepository.findByEmail(username);
	Long idUsuario = usuario.getId();
	
	Perfil perfilUsuario = perfilRepository.findByIdUsuarioId(idUsuario);
	
	Postagem postagem = new Postagem();
	postagem.setTitulo(titulo);
	postagem.setCorpo(corpo);
	postagem.setCategoria(categoria);
	postagem.setMediaUrl(mediaUrl);
	postagem.setDataPostagem(LocalDate.now());
	postagem.setPerfil(perfilUsuario);
	
	postagemService.salvar(postagem);
	
	return "redirect:/admin/postagem/lista?postSuccess";
	}
	
	
	

	@GetMapping("/admin/curso/lista")
	public String listaCursos(Model model) {
		List<Curso> cursos = cursoRepository.findAll();

		model.addAttribute("cursos", cursos);

		return "admin/curso/listaCursos.html";
	}
	
	@GetMapping("/admin/curso/detalhe")
	public String detalheCurso(@RequestParam("id") Long id, Model model) {
		Curso curso = cursoRepository.getReferenceById(id);
		
		model.addAttribute("curso",curso);
		return "admin/curso/detalheCurso.html";
	}
	
	@GetMapping("/admin/curso/delete")
	public String deleteCurso(@RequestParam("id")Long id, Model model) {
        Curso curso = cursoRepository.getReferenceById(id);
        
        cursoRepository.delete(curso);
        
        model.addAttribute("curso",curso);
		return "redirect:/admin/curso/lista?deleteSuccess";
	}
	
	@GetMapping("/admin/curso/cadastro")
	public String cadastroCurso() {
		
		return "admin/curso/cadastroCurso.html";
	}
	
	@PostMapping("/admin/curso/save")
	public String criarCurso(Principal principal,
							 @RequestParam("titulo") String titulo,
							 @RequestParam("descricaoCurta") String descricaoCurta,
							 @RequestParam("descricaoLonga") String descricaoLonga,
							 @RequestParam("categoria") String categoria,
							 @RequestParam("media_url") String mediaUrl,
							 @RequestParam("foto_capa") String fotoCapa) {
		
		String username = principal.getName();
        Usuario usuario = usuarioRepository.findByEmail(username);
        Long idUsuario = usuario.getId();
        
        Perfil perfilUsuario = perfilRepository.findByIdUsuarioId(idUsuario);
        
        
        Curso curso = new Curso();
        curso.setTitulo(titulo);
        curso.setDescricaoCurta(descricaoCurta);
        curso.setDescricaoLonga(descricaoLonga);
        curso.setCategoria(categoria);
        curso.setDataCriacao(LocalDate.now());
        curso.setMediaUrl(mediaUrl);
        curso.setFotoCapa(fotoCapa);
        curso.setIdColaborador(perfilUsuario);
        
        cursoRepository.save(curso);
        
        return "redirect:/admin/curso/lista?saveSuccess";
	}
	
	@GetMapping("/admin/curso/editar")
	public String edicaoCurso(@RequestParam("id")Long id, Model model) {
        Curso curso = cursoRepository.getReferenceById(id);
        
        model.addAttribute("curso",curso);
		return "admin/curso/editarCurso.html";
	}
	
	@PostMapping("/admin/curso/editar")
	public ModelAndView editarCurso(Principal principal,
							 @RequestParam("cursoId") Long cursoId, Curso curso)throws IOException {
		
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/curso/lista?editSuccess");
		
		 java.util.Optional<Curso> cursoExistenteOpt = cursoRepository.findById(cursoId);

		    if (cursoExistenteOpt.isPresent()) {
		        Curso cursoExistente = cursoExistenteOpt.get();

		        cursoExistente.setTitulo(curso.getTitulo());
		        cursoExistente.setCategoria(curso.getCategoria());
		        cursoExistente.setDescricaoCurta(curso.getDescricaoCurta());
		        cursoExistente.setDescricaoLonga(curso.getDescricaoLonga());
		        cursoExistente.setFotoCapa(curso.getFotoCapa());
		        cursoExistente.setMediaUrl(curso.getMediaUrl());

		        cursoRepository.save(cursoExistente);
		    }
        
        return modelAndView;
	}
	
	

	@GetMapping("/admin/inscricao/lista")
	public String listaInscricoes(Model model) {
		List<InscricaoCurso> inscricoes = inscricaoCursoRepository.findAll();

		model.addAttribute("inscricoes", inscricoes);

		return "admin/inscricao/listaInscricoes.html";
	}
	
	@GetMapping("/admin/inscricoes/detalhe")
	public String detalheInscricao(@RequestParam("id")Long id, Model model) {
		InscricaoCurso inscricao = inscricaoCursoRepository.getReferenceById(id);

		model.addAttribute("inscricao", inscricao);
	    return "admin/inscricao/detalheInscricao.html";
	}
	
	
	
	
	@GetMapping("/admin/suporte")
	public String paginaSuporte(Model model) {
		List<Suporte> contatos = suporteRepository.findAll();
		
		model.addAttribute("contatos",contatos);
		return "admin/suporte/listaSuporte.html";
	}
	
	@GetMapping("/admin/suporte/detalhe")
	public String detalheSuporte(@RequestParam("id") Long id, Model model) {
		Suporte contato = suporteRepository.getReferenceById(id);
		
		model.addAttribute("contato",contato);
		return "admin/suporte/detalheSuporte.html";
	}
	
}
