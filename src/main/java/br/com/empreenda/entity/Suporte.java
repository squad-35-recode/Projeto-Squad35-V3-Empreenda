package br.com.empreenda.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "suporte")
public class Suporte {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String email;
	
	@Column
	private String nome;
	
	@Column
	private String telefone;
	
	@Column
	private String texto;
	
	@Column(name = "data_contato")
	private LocalDate dataContato;
	
	@Column
	private Boolean whatsapp;
	
	
}
