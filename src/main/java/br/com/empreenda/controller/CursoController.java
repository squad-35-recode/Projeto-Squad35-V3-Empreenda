package br.com.empreenda.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.empreenda.entity.Curso;
import br.com.empreenda.entity.InscricaoCurso;
import br.com.empreenda.entity.Perfil;
import br.com.empreenda.entity.Usuario;
import br.com.empreenda.repository.CursoRepository;
import br.com.empreenda.repository.InscricaoCursoRepository;
import br.com.empreenda.repository.PerfilRepository;
import br.com.empreenda.repository.UsuarioRepository;

@Controller
public class CursoController {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PerfilRepository perfilRepository;

	@Autowired
	private InscricaoCursoRepository inscricaoCursoRepository;

	@GetMapping("/cursos/index")
	public String indexCursos(Model model) {
		List<Curso> cursos = cursoRepository.findAll();
		model.addAttribute("cursos", cursos);
		return "curso/cursos.html";
	}

	@GetMapping("/cursos/detalhe")
	public String detalheCurso(@RequestParam("id") Long id, Model model, Principal principal) {
		Curso curso = cursoRepository.findCursoDetails(id);
		
		if (principal != null) {
			String username = principal.getName();
			Usuario usuario = usuarioRepository.findByEmail(username);

			if (usuario != null) {
				Long idUsuario = usuario.getId();
				Perfil perfilUsuario = perfilRepository.findByIdUsuarioId(idUsuario);
				boolean inscricao = inscricaoCursoRepository.existsByCursoAndPerfil(perfilUsuario, curso);
				model.addAttribute("inscricao", inscricao);
			}
		}

		model.addAttribute("curso", curso);
		return "curso/detalheCurso.html";
	}

	@PostMapping("/cursos/inscricao/save")
	public String inscricaoCurso(@RequestParam("id") Long idCurso, Principal principal) {
		String username = principal.getName();
		Usuario usuario = usuarioRepository.findByEmail(username);
		Long idUsuario = usuario.getId();

		Perfil perfilUsuario = perfilRepository.findByIdUsuarioId(idUsuario);

		Curso curso = new Curso();
		curso.setId(idCurso);

		boolean inscricaoExist = inscricaoCursoRepository.existsByCursoAndPerfil(perfilUsuario, curso);
		System.out.println("Booleano " + inscricaoExist);

		if (!inscricaoExist) {
			InscricaoCurso inscricao = new InscricaoCurso();
			inscricao.setCurso(curso);
			inscricao.setPerfil(perfilUsuario);
			inscricao.setDataInscricao(LocalDate.now());

			inscricaoCursoRepository.save(inscricao);

			return "redirect:/cursos/detalhe?id=" + idCurso + "&inscricaoSuccess";
		} else {
			return "redirect:/cursos/detalhe?id=" + idCurso + "&inscricaoError";
		}

	}
	
	@GetMapping("/cursos/usuario")
	public String cursosUsuario(Principal principal, Model model) {
		String username = principal.getName();
		Usuario usuario = usuarioRepository.findByEmail(username);
		Long idUsuario = usuario.getId();

		Perfil perfilUsuario = perfilRepository.findByIdUsuarioId(idUsuario);
		
		List<InscricaoCurso> inscricoes = inscricaoCursoRepository.findByPerfil(perfilUsuario);
		model.addAttribute("inscricoes",inscricoes);
		return "usuario/meusCursos.html";
	}
	
	@GetMapping("/cursos/usuario/detalhe")
	public String detalheCursoUsuario(@RequestParam("id")Long idCurso, Principal principal, Model model) {
		String username = principal.getName();
		Usuario usuario = usuarioRepository.findByEmail(username);
		Long idUsuario = usuario.getId();

		Perfil perfilUsuario = perfilRepository.findByIdUsuarioId(idUsuario);
		
		List<InscricaoCurso> inscricoes = inscricaoCursoRepository.findByPerfil(perfilUsuario);
		
		Curso curso = cursoRepository.findCursoDetails(idCurso);
		
		model.addAttribute("curso", curso);
		model.addAttribute("inscricoes",inscricoes);
		return "usuario/detalheCursoUsuario.html";
	}
	
	@GetMapping("/cursos/filtrar")
	public String filtrarCursos(@RequestParam("categoria") String categoria, Model model) {
		List<Curso> cursos = cursoRepository.findByCategoria(categoria);
		
		model.addAttribute("cursos",cursos);
		return "curso/filtragemCursos.html";
	}
	
	@GetMapping("/cursos/pesquisa")
	public String pesquisaCursos(@RequestParam("pesquisa") String pesquisa, Model model) {
		List<Curso> cursos = cursoRepository.findByTituloContaining(pesquisa);
		
		model.addAttribute("cursos",cursos);
		return "curso/filtragemCursos.html";
	}
	
	
	@GetMapping("/usuario/curso/cancelar")
	public String cancelarInscricao(@RequestParam("id")Long id, Model model) {
		InscricaoCurso inscricao = inscricaoCursoRepository.getReferenceById(id);
		
		inscricaoCursoRepository.delete(inscricao);

	    return "redirect:/cursos/usuario?deleteSuccess";
	}

}
