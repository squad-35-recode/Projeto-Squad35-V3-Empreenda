package br.com.empreenda.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "curso" )
public class Curso {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String titulo;
	
	@Column(name = "descricao_curta")
	private String descricaoCurta;
	
	@Column(name = "descricao_longa")
	private String descricaoLonga;
	
	@Column(name = "data_criacao")
	private LocalDate dataCriacao;
	
	@Column
	private String categoria;
	
	@Column(name = "media_url")
	private String mediaUrl;
	
	@ManyToOne
    @JoinColumn(name = "id_colaborador")
	private Perfil idColaborador;
	
	@Column(name = "foto_capa")
	private String fotoCapa;
	
	
}
