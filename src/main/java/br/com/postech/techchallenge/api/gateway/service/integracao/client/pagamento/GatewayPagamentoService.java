package br.com.postech.techchallenge.api.gateway.service.integracao.client.pagamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import br.com.postech.techchallenge.api.gateway.configuration.PagamentoProperties;
import br.com.postech.techchallenge.api.gateway.model.request.pagamento.Pagamento;
import br.com.postech.techchallenge.api.gateway.model.response.pagamento.HistoricoPagamentoResponse;
import br.com.postech.techchallenge.api.gateway.model.response.pagamento.PagamentoResponse;
import br.com.postech.techchallenge.api.gateway.service.integracao.proxy.ServicoPagamentoProxy;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GatewayPagamentoService {

	private final PagamentoProperties properties;	
	private final ServicoPagamentoProxy proxy;	
	
	public PagamentoResponse consultarPagamentoPorPedido(Jwt principal, Long numeroPedido) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getByNumber());
		String pathParam = Objects.nonNull(numeroPedido) ? numeroPedido.toString() : null;
		
		return proxy.get(PagamentoResponse.class, pathParam);
	}
	
	public List<HistoricoPagamentoResponse> consultarHistoricoPagamentoPorPedido(Jwt principal, Long numeroPedido) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getByNumber());
		String pathParam = Objects.nonNull(numeroPedido) ? numeroPedido.toString() : null;
		
		return proxy.get(new ArrayList<HistoricoPagamentoResponse>(), pathParam);
	}
	
	public PagamentoResponse criarPagamento(Jwt principal, Pagamento pagamento) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getSave());
		
		return proxy.post(pagamento, PagamentoResponse.class);
	}
	
	public PagamentoResponse atualizaPagamento(Jwt principal, Pagamento pagamento) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getSave());
		
		return proxy.put(pagamento, PagamentoResponse.class);
	}
}
