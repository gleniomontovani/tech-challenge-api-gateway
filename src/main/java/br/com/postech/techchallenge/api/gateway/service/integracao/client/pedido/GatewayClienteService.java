package br.com.postech.techchallenge.api.gateway.service.integracao.client.pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import br.com.postech.techchallenge.api.gateway.configuration.ApiClientProperties;
import br.com.postech.techchallenge.api.gateway.configuration.ClienteProperties;
import br.com.postech.techchallenge.api.gateway.model.request.pedido.Cliente;
import br.com.postech.techchallenge.api.gateway.model.response.pedido.ClienteResponse;
import br.com.postech.techchallenge.api.gateway.service.http.Proxy;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GatewayClienteService {

	private final ClienteProperties properties;
	private final ApiClientProperties apiProperties;
	private final Proxy proxy;
	
    @PostConstruct
    public void initialization() {
        if (Objects.nonNull(apiProperties)) {
        	proxy.setEndPoint(apiProperties.getPedidoUri());
        }
    }

	public List<ClienteResponse> listarClientes(Jwt jwt) throws Exception {
		proxy.setJwt(jwt);		
		proxy.setResource(properties.getListall());

		return proxy.get(new ArrayList<ClienteResponse>());
	}

	public ClienteResponse buscarCliente(Jwt principal, Long idCliente) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getByid());
		String pathParam = Objects.nonNull(idCliente) ? idCliente.toString() : null;
		log.debug("Passou aqui no Gateway para listar o cliente: {} ", pathParam);

		return proxy.get(ClienteResponse.class, pathParam);
	}

	public ClienteResponse salvarCliente(Jwt principal, Cliente cliente) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getSave());

		return proxy.post(cliente, ClienteResponse.class);
	}

	public ClienteResponse atualizarCliente(Jwt principal, Long id, Cliente cliente) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getUpdate());
		String pathParam = Objects.nonNull(id) ? id.toString() : null;

		return proxy.put(cliente, pathParam, ClienteResponse.class);
	}

	public ClienteResponse desativarCliente(Jwt principal, Long id) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getDelete());
		String pathParam = Objects.nonNull(id) ? id.toString() : null;

		return proxy.delete(pathParam, ClienteResponse.class);
	}
}
