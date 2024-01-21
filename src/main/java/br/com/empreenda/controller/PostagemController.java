package br.com.empreenda.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.empreenda.dto.LikeResponseDTO;
import br.com.empreenda.entity.ComentariosPostagem;
import br.com.empreenda.entity.LikesPostagem;
import br.com.empreenda.entity.Perfil;
import br.com.empreenda.entity.Postagem;
import br.com.empreenda.entity.Usuario;
import br.com.empreenda.repository.ComentariosPostagemRepository;
import br.com.empreenda.repository.LikesPostagemRepository;
import br.com.empreenda.repository.PerfilRepository;
import br.com.empreenda.repository.PostagemRepository;
import br.com.empreenda.repository.UsuarioRepository;
import br.com.empreenda.service.imp.PostagemService;

@Controller
public class PostagemController {

	private final PostagemService postagemService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private PostagemRepository postagemRepository;
	
	@Autowired
	private ComentariosPostagemRepository comentariosPostagemRepository;
	
	@Autowired
	private LikesPostagemRepository likesPostagemRepository;
	
	
    public PostagemController(PostagemService postagemService) {
        this.postagemService = postagemService;
    }
    
    
    
    @PostMapping("/post/save")
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

        return "redirect:/index?postSuccess";
    }
    
    @GetMapping("/post/detalhe")
    public String detalhePostagem(@RequestParam("id") Long postId, Model model) {
    	Long postIdLong = Long.valueOf(postId);
    	Postagem postagem = postagemRepository.findAllPostagensWithUserInfoById(postIdLong);
    	List<ComentariosPostagem> comentarios = comentariosPostagemRepository.findAllCommentsByPostWithUserInfoById(postagem);
    	int numeroLikes = likesPostagemRepository.countByPostagemId(postagem.getId());
        
    	model.addAttribute("numeroLikes", numeroLikes);
    	model.addAttribute("comentarios",comentarios);
    	model.addAttribute("postagem", postagem);
        return "detalhePostagem.html"; 
    }
    
    @PostMapping("/post/comment")
    public String comentar(@RequestParam("postId") Long postId, 
    					   @RequestParam("comentario") String comentario,	 
    		               Principal principal) {
    	
    	String username = principal.getName();
    	Usuario usuario = usuarioRepository.findByEmail(username);
        Long idUsuario = usuario.getId();
        
        Perfil perfilUsuario = perfilRepository.findByIdUsuarioId(idUsuario);
        
        Postagem postagem = postagemRepository.getReferenceById(postId);
        
        ComentariosPostagem comentarioPost = new ComentariosPostagem();
        
        comentarioPost.setComentario(comentario);
        comentarioPost.setPerfil(perfilUsuario);
        comentarioPost.setPostagem(postagem);
        comentarioPost.setDataComentario(LocalDate.now());
        
        comentariosPostagemRepository.save(comentarioPost);
        
    	return "redirect:/post/detalhe?id=" + postId;
    }
    
    @PostMapping("/post/index/flag")
    public String denunciaIndex(@RequestParam("id") Long postId, Model model) {
    	Postagem postagem = new Postagem();
    	postagem = postagemRepository.getReferenceById(postId);
    	
    	postagem.setDenuncia(postagem.getDenuncia() + 1);
    	
    	postagemRepository.save(postagem);
        
        return "redirect:/index?denuncia"; 
    }
    
    @PostMapping("/post/details/flag")
    public String denunciaDetalhes(@RequestParam("postId") Long postId, Model model) {
    	Postagem postagem = new Postagem();
    	postagem = postagemRepository.getReferenceById(postId);
    	
    	postagem.setDenuncia(postagem.getDenuncia() + 1);
        
    	return "redirect:/post/detalhe?id=" + postId + "&denuncia"; 
    }
    
    @GetMapping("/post/user/delete")
    public ModelAndView excluirPostagem(@RequestParam("postId") Long id) {
    	ModelAndView modelAndView = new ModelAndView("redirect:/perfil?deletePostSuccess");
    	postagemRepository.deleteById(id);
    	return modelAndView;
    }
    
    @PostMapping("/post/like")
    public ResponseEntity<LikeResponseDTO> darLike(@RequestParam("postId") Long postId, Principal principal) {
        try {
            String username = principal.getName();
            Usuario usuario = usuarioRepository.findByEmail(username);
            Long idUsuario = usuario.getId();

            Postagem postagem = postagemRepository.getReferenceById(postId);

            LikesPostagem existente = likesPostagemRepository.findByUsuarioAndPostagem(usuario, postagem);
            if (existente == null) {
                LikesPostagem novoLike = new LikesPostagem();
                novoLike.setUsuario(usuario);
                novoLike.setPostagem(postagem);
                likesPostagemRepository.save(novoLike);

                int totalLikes = likesPostagemRepository.countByPostagem(postagem); 
                return new ResponseEntity<>(new LikeResponseDTO("Like adicionado com sucesso", true, totalLikes), HttpStatus.OK);
            } else {
                likesPostagemRepository.delete(existente);

                int totalLikes = likesPostagemRepository.countByPostagem(postagem); 
                return new ResponseEntity<>(new LikeResponseDTO("Like removido com sucesso", false, totalLikes), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new LikeResponseDTO("Erro ao processar o like", false, 0), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
