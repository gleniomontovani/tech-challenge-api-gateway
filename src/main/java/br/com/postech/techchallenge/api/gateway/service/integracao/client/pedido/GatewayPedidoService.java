package br.com.postech.techchallenge.api.gateway.service.integracao.client.pedido;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import br.com.postech.techchallenge.api.gateway.configuration.ApiClientProperties;
import br.com.postech.techchallenge.api.gateway.configuration.PedidoProperties;
import br.com.postech.techchallenge.api.gateway.model.request.pedido.Pedido;
import br.com.postech.techchallenge.api.gateway.model.response.pedido.PedidoResponse;
import br.com.postech.techchallenge.api.gateway.service.http.Proxy;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GatewayPedidoService {

	private final Proxy proxy;
	private final PedidoProperties properties;
	private final ApiClientProperties apiProperties;
	
    @PostConstruct
    public void initialization() {
        if (Objects.nonNull(apiProperties)) {
        	proxy.setEndPoint(apiProperties.getPedidoUri());
        }
    }
	
	public List<PedidoResponse> listarTodosPedidos(Jwt principal) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getListall());
		
		return proxy.get(new ArrayList<PedidoResponse>());
	}
	
	public PedidoResponse buscarPedido(Jwt principal, Long numeroPedido) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getByNumber());
		String paramPath = Objects.nonNull(numeroPedido) ? String.valueOf(numeroPedido) : null;
		
		return proxy.get(PedidoResponse.class, paramPath);
	}

	public PedidoResponse fazerCheckoutFake(Jwt principal, Pedido pedido) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getSave());
		
		return proxy.post(pedido, PedidoResponse.class);
	}
	
	public PedidoResponse atualizarStatusPedido(Jwt principal, Long numeroPedido, Integer statusPedido) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getUpdate());
		List<String> paramPath = Arrays.asList(String.valueOf(numeroPedido), String.valueOf(statusPedido));
		
		return proxy.put(paramPath, PedidoResponse.builder().build());
	}
}
