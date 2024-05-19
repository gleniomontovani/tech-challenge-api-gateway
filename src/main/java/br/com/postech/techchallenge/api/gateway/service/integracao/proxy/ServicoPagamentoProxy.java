package br.com.postech.techchallenge.api.gateway.service.integracao.proxy;

import java.util.Objects;

import org.springframework.stereotype.Component;

import br.com.postech.techchallenge.api.gateway.configuration.ApiClientProperties;
import br.com.postech.techchallenge.api.gateway.service.http.Proxy;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ServicoPagamentoProxy extends Proxy {
	private final ApiClientProperties apiProperties;
	
	@PostConstruct
    public void initialization() {
        if (Objects.nonNull(apiProperties)) {
        	setEndPoint(apiProperties.getPagamentoUri());
        }
    }
}
