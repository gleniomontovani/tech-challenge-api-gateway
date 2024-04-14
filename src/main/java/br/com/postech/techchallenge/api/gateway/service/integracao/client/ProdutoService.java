package br.com.postech.techchallenge.api.gateway.service.integracao.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import br.com.postech.techchallenge.api.gateway.configuration.ProdutoProperties;
import br.com.postech.techchallenge.api.gateway.model.Produto;
import br.com.postech.techchallenge.api.gateway.service.http.Proxy;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {

	private final Proxy proxy;
	private final ProdutoProperties properties;

	@SuppressWarnings("unchecked")
	public <T> List<T> listarProdutos(Jwt principal, Integer categoria) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getListall());
		String pathParam = Objects.nonNull(categoria) ? String.valueOf(categoria) : null;

		return proxy.get(ArrayList.class, pathParam);
	}

	@SuppressWarnings("unchecked")
	public <T> T buscarProdutoPorId(Jwt principal, Long id) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getByid());
		String pathParam = Objects.nonNull(id) ? String.valueOf(id) : null;

		return (T) proxy.get(Object.class, pathParam);
	}

	@SuppressWarnings("unchecked")
	public <T> T salvar(Jwt principal, Produto produto) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getSave());

		return (T) proxy.post(produto);
	}

	@SuppressWarnings("unchecked")
	public <T> T atualizar(Jwt principal, Integer id, Produto produto) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getUpdate());
		String pathParam = Objects.nonNull(id) ? String.valueOf(id) : null;

		return (T) proxy.put(produto, pathParam);
	}

	@SuppressWarnings("unchecked")
	public <T> T deleteById(Jwt principal, Long id) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getDelete());
		String pathParam = Objects.nonNull(id) ? String.valueOf(id) : null;

		return (T) proxy.delete(pathParam);
	}
}
