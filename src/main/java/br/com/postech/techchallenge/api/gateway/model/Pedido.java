package br.com.postech.techchallenge.api.gateway.model;

import java.util.List;

public record Pedido(Long numeroPedido, Cliente cliente, String dataPedido, Integer statusPedido,
		List<PedidoProduto> produtos) {

}
