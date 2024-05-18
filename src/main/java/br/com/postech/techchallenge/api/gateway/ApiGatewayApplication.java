package br.com.postech.techchallenge.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import br.com.postech.techchallenge.api.gateway.configuration.ApiClientProperties;
import br.com.postech.techchallenge.api.gateway.configuration.ClienteProperties;
import br.com.postech.techchallenge.api.gateway.configuration.PagamentoProperties;
import br.com.postech.techchallenge.api.gateway.configuration.PedidoProperties;
import br.com.postech.techchallenge.api.gateway.configuration.ProducaoProperties;
import br.com.postech.techchallenge.api.gateway.configuration.ProdutoProperties;

@SpringBootApplication
@EnableConfigurationProperties({ ApiClientProperties.class, ClienteProperties.class, PagamentoProperties.class,
		PedidoProperties.class, ProdutoProperties.class, ProducaoProperties.class })
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
}
