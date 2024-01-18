package br.com.empreenda.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.empreenda.entity.Curso;
import br.com.empreenda.entity.InscricaoCurso;
import br.com.empreenda.entity.Perfil;
import br.com.empreenda.entity.Usuario;
import br.com.empreenda.repository.CursoRepository;
import br.com.empreenda.repository.InscricaoCursoRepository;
import br.com.empreenda.repository.PerfilRepository;
import br.com.empreenda.repository.UsuarioRepository;

@Controller
public class ColaboradorController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private InscricaoCursoRepository inscricaoCursoRepository;
	
	@GetMapping("/colab/index")
	public String indexColab(Principal principal, Model model) {
		String username = principal.getName();
        Usuario usuario = usuarioRepository.findByEmail(username);
        
      	model.addAttribute("usuario",usuario);
		return "/colaborador/dashboardColaborador";
	}
	
	
	@GetMapping("/colab/curso/lista")
	public ModelAndView listaCursos(Principal principal, Model model) {
		ModelAndView modelAndView = new ModelAndView("colaborador/listaCursos.html");
		String username = principal.getName();
        Usuario usuario = usuarioRepository.findByEmail(username);
        Long idUsuario = usuario.getId();
        
        Perfil perfilUsuario = perfilRepository.findByIdUsuarioId(idUsuario);
        
        List<Curso> cursos = cursoRepository.findAllByIdColaborador(perfilUsuario);
        
        model.addAttribute("cursos",cursos);
		return modelAndView;
	}
	
	@GetMapping("/colab/curso/detalhe")
	public String detalheCurso(@RequestParam("id")Long id, Model model) {
        Curso curso = cursoRepository.getReferenceById(id);
        
        model.addAttribute("curso",curso);
		return "colaborador/detalheCurso.html";
	}
	
	@GetMapping("/colab/curso/cadastro")
	public String cadastroCurso() {
		
		return "colaborador/cadastroCurso.html";
	}
	
	@PostMapping("/colab/curso/save")
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
        
        return "redirect:/colab/curso/lista?saveSuccess";
	}
	
	@GetMapping("/colab/curso/editar")
	public String edicaoCurso(@RequestParam("id")Long id, Model model) {
        Curso curso = cursoRepository.getReferenceById(id);
        
        model.addAttribute("curso",curso);
		return "colaborador/editarCurso.html";
	}
	
	@PostMapping("/colab/curso/editar")
	public ModelAndView editarCurso(Principal principal,
							 @RequestParam("cursoId") Long cursoId, Curso curso)throws IOException {
		
		ModelAndView modelAndView = new ModelAndView("redirect:/colab/curso/lista?editSuccess");
		
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
	
	@GetMapping("/colab/curso/delete")
	public String deleteCurso(@RequestParam("id")Long id, Model model) {
        Curso curso = cursoRepository.getReferenceById(id);
        
        cursoRepository.delete(curso);
        
        model.addAttribute("curso",curso);
		return "redirect:/colab/curso/lista?deleteSuccess";
	}
	
	@GetMapping("/colab/inscricoes")
	public String inscricoes(Model model, Principal principal) {
	    String username = principal.getName();
	    Usuario usuario = usuarioRepository.findByEmail(username);
	    Long idUsuario = usuario.getId();
	    
	    Perfil perfilUsuario = perfilRepository.findByIdUsuarioId(idUsuario);

	    List<InscricaoCurso> inscricoes = inscricaoCursoRepository.findInscricoesByColaboradorId(perfilUsuario);

	    model.addAttribute("inscricoes", inscricoes);

	    return "colaborador/listaInscricoes.html";
	}
	
	@GetMapping("/colab/inscricoes/detalhe")
	public String detalheInscricao(@RequestParam("id")Long id, Model model) {
		InscricaoCurso inscricao = inscricaoCursoRepository.getReferenceById(id);

		model.addAttribute("inscricao", inscricao);
	    return "colaborador/detalheInscricao.html";
	}
	
	@GetMapping("/colab/inscricoes/cancelar")
	public String cancelarInscricaoColab(@RequestParam("id")Long id, Model model) {
		InscricaoCurso inscricao = inscricaoCursoRepository.getReferenceById(id);
		
		inscricaoCursoRepository.delete(inscricao);

	    return "redirect:/colab/curso/lista?deleteSuccess";
	}


}
