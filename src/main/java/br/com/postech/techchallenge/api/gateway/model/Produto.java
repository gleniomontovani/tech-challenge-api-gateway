package br.com.postech.techchallenge.api.gateway.model;

import java.math.BigDecimal;
import java.util.List;

public record Produto(String nome, Integer categoria, BigDecimal valor, String descricao, List<ProdutoImagem> imagens) {

}
