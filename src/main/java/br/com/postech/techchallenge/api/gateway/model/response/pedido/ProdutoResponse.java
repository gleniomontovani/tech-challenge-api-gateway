package br.com.postech.techchallenge.api.gateway.model.response.pedido;

import java.math.BigDecimal;
import java.util.List;

import br.com.postech.techchallenge.api.gateway.model.request.pedido.ProdutoImagem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponse {

	private Long id;
    private String nome;
    private Integer categoria;
    private BigDecimal valor;
    private String descricao;
    private List<ProdutoImagem> imagens;
}
