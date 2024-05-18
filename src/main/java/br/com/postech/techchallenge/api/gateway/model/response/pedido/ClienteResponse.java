package br.com.postech.techchallenge.api.gateway.model.response.pedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponse {

	private Long numero; 
	private String nome; 
	private String email; 
	private String cpf; 
	private Boolean status;
}
