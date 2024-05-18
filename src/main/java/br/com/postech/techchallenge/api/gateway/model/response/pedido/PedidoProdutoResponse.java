package br.com.postech.techchallenge.api.gateway.model.response.pedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoProdutoResponse {

	private ProdutoResponse produto;
    private Integer quantidade;
}
