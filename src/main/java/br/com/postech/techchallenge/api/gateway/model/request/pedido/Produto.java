package br.com.postech.techchallenge.api.gateway.model.request.pedido;

import java.math.BigDecimal;
import java.util.List;

public record Produto(Long id, String nome, Integer categoria, BigDecimal valor, String descricao, List<ProdutoImagem> imagens) {

}
