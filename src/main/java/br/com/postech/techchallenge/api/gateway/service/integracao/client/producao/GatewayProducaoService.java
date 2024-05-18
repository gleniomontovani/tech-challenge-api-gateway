package br.com.postech.techchallenge.api.gateway.service.integracao.client.producao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import br.com.postech.techchallenge.api.gateway.configuration.ApiClientProperties;
import br.com.postech.techchallenge.api.gateway.configuration.ProducaoProperties;
import br.com.postech.techchallenge.api.gateway.model.request.producao.Producao;
import br.com.postech.techchallenge.api.gateway.model.response.producao.ProducaoResponse;
import br.com.postech.techchallenge.api.gateway.service.http.Proxy;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GatewayProducaoService {

	private final ProducaoProperties properties;
	private final ApiClientProperties apiProperties;
	private final Proxy proxy;
	
    @PostConstruct
    public void initialization() {
        if (Objects.nonNull(apiProperties)) {
        	proxy.setEndPoint(apiProperties.getPedidoUri());
        }
    }
    
    public List<ProducaoResponse> listarTodasProducaoPorSituacao(Jwt principal, Integer situacao) throws Exception {
    	proxy.setJwt(principal);		
		proxy.setResource(properties.getBySituacao());
		String pathParam = Objects.nonNull(situacao) ? situacao.toString() : null;
		
		return proxy.get(new ArrayList<ProducaoResponse>(), pathParam);
	}

	public ProducaoResponse buscarProducaoPorNumeroPedido(Jwt principal, Long numeroPedido) throws Exception {
		proxy.setJwt(principal);		
		proxy.setResource(properties.getByNumeroPedido());
		
		String pathParam = Objects.nonNull(numeroPedido) ? numeroPedido.toString() : null;

		return proxy.get(ProducaoResponse.class, pathParam);		
	}

	public ProducaoResponse salvarProducaoPedido(Jwt principal, Producao producao) throws Exception {
		proxy.setJwt(principal);		
		proxy.setResource(properties.getSave());
		
		return proxy.post(producao, ProducaoResponse.class);
	}
	
	public ProducaoResponse atualizarStatusProducao(Jwt principal, Producao producao) throws Exception {
		proxy.setJwt(principal);		
		proxy.setResource(properties.getUpade());
		
		return proxy.post(producao, ProducaoResponse.class);
	}
}
