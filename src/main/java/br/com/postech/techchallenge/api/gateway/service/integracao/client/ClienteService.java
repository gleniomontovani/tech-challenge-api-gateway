package br.com.postech.techchallenge.api.gateway.service.integracao.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import br.com.postech.techchallenge.api.gateway.configuration.ClienteProperties;
import br.com.postech.techchallenge.api.gateway.model.Cliente;
import br.com.postech.techchallenge.api.gateway.service.http.Proxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {

	private final ClienteProperties properties;
	private final Proxy proxy;

	@SuppressWarnings("unchecked")
	public <T> List<T> listarClientes(Jwt jwt) throws Exception {
		proxy.setJwt(jwt);
		proxy.setResource(properties.getListall());

		return proxy.get(ArrayList.class);
	}

	@SuppressWarnings("unchecked")
	public <T> T buscarCliente(Jwt principal, Long idCliente) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getByid());
		String pathParam = Objects.nonNull(idCliente) ? idCliente.toString() : null;
		log.debug("Passou aqui no Gateway para listar o cliente: {} ", pathParam);

		return (T) proxy.get(Object.class, pathParam);
	}

	@SuppressWarnings("unchecked")
	public <T> T salvarCliente(Jwt principal, Cliente cliente) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getSave());

		return (T) proxy.post(cliente);
	}

	@SuppressWarnings("unchecked")
	public <T> T atualizarCliente(Jwt principal, Long id, Cliente cliente) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getUpdate());
		String pathParam = Objects.nonNull(id) ? id.toString() : null;

		return (T) proxy.put(cliente, pathParam);
	}

	@SuppressWarnings("unchecked")
	public <T> T desativarCliente(Jwt principal, Long id) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getDelete());
		String pathParam = Objects.nonNull(id) ? id.toString() : null;

		return (T) proxy.delete(pathParam);
	}
}
