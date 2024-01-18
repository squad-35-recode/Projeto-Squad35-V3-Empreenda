package br.com.empreenda.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "endereco_perfil")
public class EnderecoPerfil {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String logradouro;
	
	@Column
	private String cidade;
	
	@Column
	private String cep;
	
	@Column
	private String uf;
	
	@Column(name = "compartilhar_endereco")
	private Boolean compartilharEndereco;
	
	@OneToOne
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

}
