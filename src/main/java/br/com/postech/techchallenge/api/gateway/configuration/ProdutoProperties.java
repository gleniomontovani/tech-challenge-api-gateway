package br.com.postech.techchallenge.api.gateway.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "api.client.server.produto.path")
public class ProdutoProperties {

	private String listall;
	private String byid;
	private String save;
	private String update;
	private String delete;
}
