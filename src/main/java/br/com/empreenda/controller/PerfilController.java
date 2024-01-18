package br.com.empreenda.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.empreenda.entity.AdicionaisPerfil;
import br.com.empreenda.entity.EnderecoPerfil;
import br.com.empreenda.entity.Perfil;
import br.com.empreenda.entity.Postagem;
import br.com.empreenda.entity.Usuario;
import br.com.empreenda.repository.AdicionaisPerfilRepository;
import br.com.empreenda.repository.ComentariosPostagemRepository;
import br.com.empreenda.repository.EnderecoPerfilRepository;
import br.com.empreenda.repository.PerfilRepository;
import br.com.empreenda.repository.PostagemRepository;
import br.com.empreenda.repository.UsuarioRepository;
import br.com.empreenda.service.imp.AdicionaisService;
import br.com.empreenda.service.imp.EnderecoService;
import br.com.empreenda.service.imp.PerfilService;
import br.com.empreenda.service.imp.UsuarioService;

@Controller
public class PerfilController {

	@SuppressWarnings("unused")
	private UsuarioService usuarioService;

	private EnderecoService enderecoService;

	private AdicionaisService adicionaisService;

	private PerfilService perfilService;

	@SuppressWarnings("unused")
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@SuppressWarnings("unused")
	@Autowired
	private PostagemRepository postagemRepository;
	
	@Autowired
	private AdicionaisPerfilRepository adicionaisPerfilRepository;
	
	@Autowired
	private EnderecoPerfilRepository enderecoPerfilRepository;
	
	@Autowired
	private ComentariosPostagemRepository comentariosPostagemRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	public PerfilController(UsuarioService usuarioService, EnderecoService enderecoService,
			AdicionaisService adicionaisService, PerfilService perfilService, PasswordEncoder passwordEncoder) {
		super();
		this.usuarioService = usuarioService;
		this.enderecoService = enderecoService;
		this.adicionaisService = adicionaisService;
		this.perfilService = perfilService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@GetMapping("/perfil")
	public String exibirPerfil(Model model, Principal principal) {

	    if (principal != null) {
	        String username = principal.getName();
	        Usuario usuario = usuarioRepository.findByEmail(username);

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
	            
	            List<Postagem> postagens = postagemRepository.findAllPostagensWithUserInfoByIdPerfil(perfilId);
	    		
	    		List<Integer> contagemComentarios = new ArrayList<>();
	    		
	    		for (Postagem postagem : postagens) {
	    	        int numeroComentarios = comentariosPostagemRepository.countByPostagemId(postagem.getId());
	    	        contagemComentarios.add(numeroComentarios);
	    	    }
	    		
	    		model.addAttribute("postagens" ,postagens);
	    		model.addAttribute("contagemComentarios", contagemComentarios);
	            model.addAttribute("adicionais", informacoesAdicionais);
	            model.addAttribute("endereco", endereco);
	            model.addAttribute("perfil", perfil);
	            model.addAttribute("usuario", usuario);
	        }
	    }

	    return "usuario/perfil.html"; 
	}
	
	@PostMapping("/perfil/save/area1")
	public ModelAndView atualizarArea1(@RequestParam("perfilId") Long perfilId, Perfil perfil) throws IOException {
		ModelAndView modelAndView = new ModelAndView("redirect:/perfil?editSuccess");
		
		Optional<Perfil> perfilExistenteOpt = perfilRepository.findById(perfilId);
		Perfil perfilExistente = perfilExistenteOpt.get();
		
		perfilExistente.setFotoUrl(perfil.getFotoUrl());
		perfilExistente.setOcupacao(perfil.getOcupacao());
		perfilExistente.setBio(perfil.getBio());
		
		perfilRepository.save(perfilExistente);
		return modelAndView;	
	}
	
	@PostMapping("/perfil/save/area2")
	public ModelAndView atualizarArea2(@RequestParam("perfilId") Long perfilId, AdicionaisPerfil adicionais) throws IOException {
		ModelAndView modelAndView = new ModelAndView("redirect:/perfil?editSuccess");
		
		Optional<AdicionaisPerfil> adicionaisExistentesOpt = Optional.of(adicionaisPerfilRepository.findByPerfilId(perfilId));
		AdicionaisPerfil adicionaisExistentes = adicionaisExistentesOpt.get();
		
		adicionaisExistentes.setContato1(adicionais.getContato1());
		adicionaisExistentes.setContato2(adicionais.getContato2());
		adicionaisExistentes.setContato3(adicionais.getContato3());
		adicionaisExistentes.setContato4(adicionais.getContato4());
		
		adicionaisPerfilRepository.save(adicionaisExistentes);
		return modelAndView;

	
	}
	
	@PostMapping("/perfil/save/area3")
	public ModelAndView atualizarArea3(@RequestParam("usuarioId") Long usuarioId, 
									   @RequestParam("perfilId") Long perfilId, 
									   @RequestParam(name = "compartilhar_email") boolean compartilharEmail,
	                                   @RequestParam(name = "compartilhar_telefone1") boolean compartilharTelefone1,
	                                   @RequestParam(name = "compartilhar_telefone2") boolean compartilharTelefone2,
	                                   @RequestParam(name = "compartilhar_endereco") boolean compartilharEndereco,
									   Perfil perfil, Usuario usuario, EnderecoPerfil endereco) throws IOException {
		ModelAndView modelAndView = new ModelAndView("redirect:/perfil?editSuccess");
		
		Optional<Usuario> usuarioExistenteOpt = usuarioRepository.findById(usuarioId);
		Usuario usuarioExistente = usuarioExistenteOpt.get();
		
		usuarioExistente.setNome(usuario.getNome());
		usuarioExistente.setCompartilharEmail(compartilharEmail);
		usuarioRepository.save(usuarioExistente);
		
		Optional<Perfil> perfilExistenteOpt = Optional.of(perfilRepository.findByIdUsuarioId(usuarioId));
		Perfil perfilExistente = perfilExistenteOpt.get();
		
		perfilExistente.setTelefone1(perfil.getTelefone1());
		perfilExistente.setCompartilharTelefone1(compartilharTelefone1);
		perfilExistente.setTelefone2(perfil.getTelefone2());
		perfilExistente.setCompartilharTelefone2(compartilharTelefone2);
		
		perfilRepository.save(perfilExistente);
		
		Optional<EnderecoPerfil> enderecoExistenteOpt = Optional.of(enderecoPerfilRepository.findByPerfilId(perfilId));
		EnderecoPerfil enderecoExistente = enderecoExistenteOpt.get();
		
		enderecoExistente.setLogradouro(endereco.getLogradouro());
		enderecoExistente.setCep(endereco.getCep());
		enderecoExistente.setCidade(endereco.getCidade());
		enderecoExistente.setUf(endereco.getUf());
		enderecoExistente.setCompartilharEndereco(compartilharEndereco);
		enderecoPerfilRepository.save(enderecoExistente);
		
		
		return modelAndView;
	}
	
	@PostMapping("/perfil/save/area4")
	public ModelAndView atualizarArea4(@RequestParam("usuarioId") Long usuarioId, Usuario usuario) throws IOException {
		ModelAndView modelAndView = new ModelAndView("redirect:/perfil?editSuccess");
		
		Optional<Usuario> usuarioExistenteOpt = usuarioRepository.findById(usuarioId);
		Usuario usuarioExistente = usuarioExistenteOpt.get();
		
		usuarioExistente.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuarioRepository.save(usuarioExistente);
		
		return modelAndView;
	}
	
	@PostMapping("/perfil/save/area5")
	public ModelAndView atualizarArea5(@RequestParam("perfilId") Long perfilId, Perfil perfil) throws IOException {
		ModelAndView modelAndView = new ModelAndView("redirect:/perfil?editSuccess");
		
		Optional<Perfil> perfilExistenteOpt = perfilRepository.findById(perfilId);
		Perfil perfilExistente = perfilExistenteOpt.get();
		
		perfilExistente.setDataNas(perfil.getDataNas());
		perfilExistente.setEscolaridade(perfil.getEscolaridade());
		perfilExistente.setRenda(perfil.getRenda());
		perfilRepository.save(perfilExistente);
		
		return modelAndView;
	}
	
	@PostMapping("/perfil/save/area6")
	public ModelAndView atualizarArea6(@RequestParam("perfilId") Long perfilId, AdicionaisPerfil adicional) throws IOException {
		ModelAndView modelAndView = new ModelAndView("redirect:/perfil?editSuccess");
		
		Optional<AdicionaisPerfil> adicionaisExistentesOpt = Optional.of(adicionaisPerfilRepository.findByPerfilId(perfilId));
		AdicionaisPerfil adicionaisExistentes = adicionaisExistentesOpt.get();
		
		
		adicionaisExistentes.setInteresse1(adicional.getInteresse1());
		adicionaisExistentes.setInteresse2(adicional.getInteresse2());
		adicionaisExistentes.setInteresse3(adicional.getInteresse3());
		
		adicionaisPerfilRepository.save(adicionaisExistentes);
		
		return modelAndView;
	}
	
	
	@GetMapping("/perfil/details")
	public String exibirPerfilVisualizacao(@RequestParam("perfilId") Long perfilId, Model model, Principal principal) {
		Perfil perfil = new Perfil();
		perfil = perfilRepository.getReferenceById(perfilId);
		
		AdicionaisPerfil adicionais = new AdicionaisPerfil();
		adicionais = adicionaisPerfilRepository.findByPerfilId(perfilId);
		
		EnderecoPerfil endereco = new EnderecoPerfil();
		endereco = enderecoPerfilRepository.findByPerfilId(perfilId);
		
		List<Postagem> postagens = postagemRepository.findAllPostagensWithUserInfoByIdPerfil(perfilId);
		
		List<Integer> contagemComentarios = new ArrayList<>();
		
		for (Postagem postagem : postagens) {
	        int numeroComentarios = comentariosPostagemRepository.countByPostagemId(postagem.getId());
	        contagemComentarios.add(numeroComentarios);
	    }
		
		model.addAttribute("postagens" ,postagens);
		model.addAttribute("contagemComentarios", contagemComentarios);
		model.addAttribute("endereco", endereco);
		model.addAttribute("adicionais", adicionais);
		model.addAttribute("perfil", perfil);
	    return "usuario/perfilVisualizacao.html"; 
	}
}
