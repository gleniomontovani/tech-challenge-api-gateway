package br.com.postech.techchallenge.api.gateway.model.request.pagamento;

import java.math.BigDecimal;

public record Pagamento(Long numeroPagamento, Long numeroPedido, Integer statusPagamento, BigDecimal valor, String qrCodePix) {

}
