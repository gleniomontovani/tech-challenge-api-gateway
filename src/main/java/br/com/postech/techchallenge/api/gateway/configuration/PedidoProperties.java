package br.com.postech.techchallenge.api.gateway.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "api.client.server.pedido.path")
public class PedidoProperties {
	
	private String listall;
	private String byNumber;
	private String save;
	private String update;
	private String delete;
}
