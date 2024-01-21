package br.com.empreenda.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "postagem")
public class Postagem {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String titulo;
	
	@Column(nullable = false)
	private String corpo;
	
	@Column(name = "media_url")
	private String mediaUrl;
	
	@Column(nullable = false)
	private String categoria;
	
	@Column(name = "data_postagem")
	private LocalDate dataPostagem;
	
	@Column
	private int denuncia;
	
	@ManyToOne 
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;
	
	@OneToMany(mappedBy = "postagem", cascade = CascadeType.ALL)
    private List<ComentariosPostagem> comentarios;
	
	@OneToMany(mappedBy = "postagem")
    private List<LikesPostagem> likes;

    public int contarLikes() {
        return (likes != null) ? likes.size() : 0;
    }

	
}
