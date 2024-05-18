package br.com.postech.techchallenge.api.gateway.model.response.pedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponse {

	private Long numeroPedido;
	private Long numeroPagamento;
    private ClienteResponse cliente;
    private String dataPedido;
    private Integer statusPedido;
    private Integer statusPagamento;
    private String qrCodePix;
}
