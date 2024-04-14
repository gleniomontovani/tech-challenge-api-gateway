package br.com.postech.techchallenge.api.gateway.service.integracao.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import br.com.postech.techchallenge.api.gateway.configuration.PagamentoProperties;
import br.com.postech.techchallenge.api.gateway.service.http.Proxy;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagamentoService {
	
	private final Proxy proxy;
	private final PagamentoProperties properties;
	
	@SuppressWarnings("unchecked")
	public <T> T consultarPagamentoPorPedido(Jwt principal, Long numeroPedido) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getByNumber());
		String pathParam = Objects.nonNull(numeroPedido) ? numeroPedido.toString() : null;
		
		return (T)proxy.get(Object.class, pathParam);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> consultarHistoricoPagamentoPorPedido(Jwt principal, Long numeroPedido) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getByNumber());
		String pathParam = Objects.nonNull(numeroPedido) ? numeroPedido.toString() : null;
		
		return proxy.get(ArrayList.class, pathParam);
	}
}
