package br.com.postech.techchallenge.api.gateway.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "api.client.server.producao.path")
public class ProducaoProperties {

	private String bySituacao;
	private String byNumeroPedido;
	private String save;
	private String upade;
}
