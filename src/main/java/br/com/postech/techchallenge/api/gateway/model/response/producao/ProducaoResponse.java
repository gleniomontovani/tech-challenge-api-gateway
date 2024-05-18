package br.com.postech.techchallenge.api.gateway.model.response.producao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProducaoResponse {

	private Long id;
	private Long numeroPedido;
	private String observacao;
	private String statusPedido;
	private String dataInicioPreparo;
	private String dataFimPreparo;
}
