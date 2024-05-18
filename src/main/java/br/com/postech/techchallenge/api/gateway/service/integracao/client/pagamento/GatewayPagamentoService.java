package br.com.postech.techchallenge.api.gateway.service.integracao.client.pagamento;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import br.com.postech.techchallenge.api.gateway.configuration.ApiClientProperties;
import br.com.postech.techchallenge.api.gateway.configuration.PagamentoProperties;
import br.com.postech.techchallenge.api.gateway.model.response.pagamento.PagamentoResponse;
import br.com.postech.techchallenge.api.gateway.service.http.Proxy;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GatewayPagamentoService {
	
	private final Proxy proxy;
	private final PagamentoProperties properties;
	private final ApiClientProperties apiProperties;
	
    @PostConstruct
    public void initialization() {
        if (Objects.nonNull(apiProperties)) {
        	proxy.setEndPoint(apiProperties.getPagamentoUri());
        }
    }
	
	public PagamentoResponse consultarPagamentoPorPedido(Jwt principal, Long numeroPedido) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getByNumber());
		String pathParam = Objects.nonNull(numeroPedido) ? numeroPedido.toString() : null;
		
		return proxy.get(PagamentoResponse.class, pathParam);
	}
	
	public List<PagamentoResponse> consultarHistoricoPagamentoPorPedido(Jwt principal, Long numeroPedido) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getByNumber());
		String pathParam = Objects.nonNull(numeroPedido) ? numeroPedido.toString() : null;
		
		return proxy.get(new ArrayList<PagamentoResponse>(), pathParam);
	}
}
