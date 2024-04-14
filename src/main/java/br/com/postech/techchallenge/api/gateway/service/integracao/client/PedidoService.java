package br.com.postech.techchallenge.api.gateway.service.integracao.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import br.com.postech.techchallenge.api.gateway.configuration.PedidoProperties;
import br.com.postech.techchallenge.api.gateway.model.Pedido;
import br.com.postech.techchallenge.api.gateway.service.http.Proxy;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {

	private final Proxy proxy;
	private final PedidoProperties properties;
	
	@SuppressWarnings("unchecked")
	public <T> List<T> listarTodosPedidos(Jwt principal) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getListall());
		
		return proxy.get(ArrayList.class);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T buscarPedido(Jwt principal, Long numeroPedido) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getByNumber());
		String paramPath = Objects.nonNull(numeroPedido) ? String.valueOf(numeroPedido) : null;
		
		return (T) proxy.get(Object.class, paramPath);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T fazerCheckoutFake(Jwt principal, Pedido pedido) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getSave());
		
		return (T) proxy.post(pedido);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T atualizarStatusPedido(Jwt principal, Long numeroPedido, Integer statusPedido) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getUpdate());
		List<String> paramPath = Arrays.asList(String.valueOf(numeroPedido), String.valueOf(statusPedido));
		
		return (T) proxy.put(paramPath, Object.class);
	}
}
