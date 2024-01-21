package br.com.empreenda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class LikeResponseDTO {

	private String message;
    private boolean liked;
    private int totalLikes;
    
   
}
