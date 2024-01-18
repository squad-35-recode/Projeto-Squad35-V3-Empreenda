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
@Table(name = "adicionais_perfil")
public class AdicionaisPerfil {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String interesse1;
	@Column
	private String interesse2;
	@Column
	private String interesse3;
	
	@Column
	private String contato1;
	@Column
	private String contato2;
	@Column
	private String contato3;
	@Column
	private String contato4;
	
	@OneToOne
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

	public AdicionaisPerfil get() {
		// TODO Auto-generated method stub
		return null;
	}
}
