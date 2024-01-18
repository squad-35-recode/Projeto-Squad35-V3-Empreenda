package br.com.empreenda.entity;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
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
@Table(name = "perfil" )
public class Perfil {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String telefone1;
	
	@Column
	private String telefone2; 

	@Column
    private String bio;
	
	@Column
	private String renda;
	
	@Column
	private String escolaridade;
	
	@Column(name = "foto_url")
	private String fotoUrl;
	
	@Column
	private String ocupacao;
	
	@Column(name = "data_nas")
	private LocalDate dataNas;
	
	@Column(name = "compartilhar_telefone1")
	private Boolean compartilharTelefone1;
	
	@Column(name = "compartilhar_telefone2")
	private Boolean compartilharTelefone2;
	
	@OneToOne(mappedBy = "perfil", cascade = CascadeType.ALL) 
    private EnderecoPerfil endereco;
	
	@OneToOne(mappedBy = "perfil", cascade = CascadeType.ALL)
    private AdicionaisPerfil adicionais;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id") 
    private Usuario usuario;
	
	
	}
