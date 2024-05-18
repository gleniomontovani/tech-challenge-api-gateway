package br.com.postech.techchallenge.api.gateway.service.integracao.client.pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import br.com.postech.techchallenge.api.gateway.configuration.ApiClientProperties;
import br.com.postech.techchallenge.api.gateway.configuration.ProdutoProperties;
import br.com.postech.techchallenge.api.gateway.model.request.pedido.Produto;
import br.com.postech.techchallenge.api.gateway.model.response.pedido.ProdutoResponse;
import br.com.postech.techchallenge.api.gateway.service.http.Proxy;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GatewayProdutoService {

	private final Proxy proxy;
	private final ProdutoProperties properties;
	private final ApiClientProperties apiProperties;
	
    @PostConstruct
    public void initialization() {
        if (Objects.nonNull(apiProperties)) {
        	proxy.setEndPoint(apiProperties.getPedidoUri());
        }
    }

	public List<ProdutoResponse> listarProdutos(Jwt principal, Integer categoria) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getListall());
		String pathParam = Objects.nonNull(categoria) ? String.valueOf(categoria) : null;

		return proxy.get(new ArrayList<ProdutoResponse>(), pathParam);
	}

	public ProdutoResponse buscarProdutoPorId(Jwt principal, Long id) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getByid());
		String pathParam = Objects.nonNull(id) ? String.valueOf(id) : null;

		return proxy.get(ProdutoResponse.class, pathParam);
	}

	public ProdutoResponse salvar(Jwt principal, Produto produto) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getSave());

		return proxy.post(produto, ProdutoResponse.class);
	}

	public ProdutoResponse atualizar(Jwt principal, Integer id, Produto produto) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getUpdate());
		String pathParam = Objects.nonNull(id) ? String.valueOf(id) : null;

		return proxy.put(produto, pathParam, ProdutoResponse.class);
	}

	public ProdutoResponse deleteById(Jwt principal, Long id) throws Exception {
		proxy.setJwt(principal);
		proxy.setResource(properties.getDelete());
		String pathParam = Objects.nonNull(id) ? String.valueOf(id) : null;

		return proxy.delete(pathParam, ProdutoResponse.class);
	}
}
