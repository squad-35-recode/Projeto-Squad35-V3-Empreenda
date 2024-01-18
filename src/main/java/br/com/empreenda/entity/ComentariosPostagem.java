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
@Table(name = "comentarios_postagem")
public class ComentariosPostagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String comentario;
	
	@Column(name = "data_comentario")
	private LocalDate dataComentario;
	
	@ManyToOne
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;
	
	@ManyToOne
    @JoinColumn(name = "postagem_id")
    private Postagem postagem;
	
	
}
