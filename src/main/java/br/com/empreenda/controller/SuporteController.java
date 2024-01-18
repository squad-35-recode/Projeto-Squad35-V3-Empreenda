package br.com.empreenda.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.empreenda.entity.Suporte;
import br.com.empreenda.repository.SuporteRepository;

@Controller
public class SuporteController {
	
	@Autowired
	private SuporteRepository suporteRepository; 

	@GetMapping("/ajuda")
	public String ajuda() {
		return "ajuda.html";
	}
	
	@PostMapping("/ajuda/save")
	public String saveSuporte(@RequestParam("nome") String nome,
							  @RequestParam("email") String email,
							  @RequestParam("telefone") String telefone,
							  @RequestParam("texto") String texto,
							  @RequestParam(value = "wapp", required = false) Boolean wapp) {
		
		Suporte suporte = new Suporte();
		suporte.setNome(nome);
		suporte.setEmail(email);
		suporte.setTelefone(telefone);
		suporte.setTexto(texto);
		suporte.setDataContato(LocalDate.now());
		suporte.setWhatsapp(wapp);
		
		suporteRepository.save(suporte);
		
		return "redirect:/ajuda?msgSuccess";
	}
}
