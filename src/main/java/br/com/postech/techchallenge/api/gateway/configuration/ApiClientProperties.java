package br.com.postech.techchallenge.api.gateway.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "api.client.server")
public class ApiClientProperties {

	private String uri;
}
