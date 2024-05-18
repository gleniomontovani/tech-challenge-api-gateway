package br.com.postech.techchallenge.api.gateway.service.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import br.com.postech.techchallenge.api.gateway.service.exception.BusinessException;
import br.com.postech.techchallenge.api.gateway.service.serializer.DateDeserializer;
import br.com.postech.techchallenge.api.gateway.service.serializer.DateSerializer;
import br.com.postech.techchallenge.api.gateway.service.util.Util;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@Data
@RequiredArgsConstructor
@Slf4j
public class Proxy implements HttpAdapter {
	private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Date.class, getDateSerializer())
			.registerTypeAdapter(Date.class, getDateDeserializer()).setPrettyPrinting().create();

	private static final String AUTORIZATION = "Authorization";
	private static final String BEARER = "Bearer ";

	private static final int OK = 200;
	private static final int CREATED = 201;
	private Jwt jwt;
	private String resource;
	private String endPoint;

	@Override
	public <T> T get(Class<T> tipo) throws Exception {
		T objeto = null;
		Reader reader = null;

		HttpClient client = null;
		HttpGet request = null;
		HttpResponse response = null;

		log.info("Executando chamada get ao recurso: " + this.endPoint + this.resource + ". Tipo: " + tipo);
		try {

			client = HttpClientBuilder.create().build();

			request = new HttpGet(this.endPoint + this.resource);
			this.configureHeader(request);

			response = client.execute(request);
			int codigoRetorno = response.getStatusLine().getStatusCode();
			if (OK == codigoRetorno) {
				reader = getReader(response.getEntity().getContent());
				objeto = GSON.fromJson(reader, tipo);
			} else {
				return processarRetorno(response, tipo);
			}
		} catch (IOException e) {
			tratarException(e);
		} finally {
			finalizar(reader, request);
		}

		return objeto;
	}

	@Override
	public <T> List<T> get(List<T> type) throws Exception {
		Reader reader = null;
		List<T> colecao = null;

		HttpClient client = null;
		HttpGet request = null;
		HttpResponse response = null;

		log.info("Executando chamada get ao recurso: " + this.endPoint + this.resource);

		try {
			client = HttpClientBuilder.create().build();

			request = new HttpGet(this.endPoint + this.resource);
			this.configureHeader(request);

			response = client.execute(request);

			int codigoRetorno = response.getStatusLine().getStatusCode();
			if (OK == codigoRetorno) {
				reader = getReader(response.getEntity().getContent());
				colecao = GSON.fromJson(reader, new ProxyTokenType<T>().getType());
			} else {
				return processarRetorno(response, type.getClass());
			}

		} catch (IOException e) {
			tratarException(e);
		} finally {
			finalizar(reader, request);
		}

		return colecao;
	}
	
	@Override
	public <T> List<T>  get(List<T> type, String pathParam) throws Exception {
		StringBuilder newURL = new StringBuilder(this.resource);
		if (StringUtils.isNotBlank(pathParam)) {
			newURL.append("/").append(pathParam);
		}
		setResource(newURL.toString());
		return get(type);
	}

	@Override
	public <T> T get(Class<T> tipo, String pathParam) throws Exception {
		StringBuilder newURL = new StringBuilder(this.resource);
		if (StringUtils.isNotBlank(pathParam)) {
			newURL.append("/").append(pathParam);
		}
		setResource(newURL.toString());
		return get(tipo);
	}

	@Override
	public <T> T get(Class<T> tipo, List<String> pathParam) throws Exception {
		StringBuilder newURL = new StringBuilder(this.resource);
		if (!Util.isNullOrEmpty(pathParam)) {
			for (String vlParametro : pathParam) {
				newURL.append("/").append(vlParametro);
			}
		}
		setResource(newURL.toString());
		return get(tipo);

	}

	@Override
	public <V, T> V post(T objeto, Class<V> type) throws Exception {
		HttpClient client = null;
		HttpPost request = null;
		HttpResponse response = null;
		try {
			client = HttpClientBuilder.create().build();

			request = new HttpPost(this.endPoint + this.resource);
			this.configureHeader(request);

			StringEntity objetoJson = new StringEntity(GSON.toJson(objeto), ContentType.APPLICATION_JSON);
			request.setEntity(objetoJson);

			response = client.execute(request);
			return processarRetorno(response, type.getClass());

		} catch (Exception e) {
			throw new BusinessException("Falha ao fazer a requisição ao servidor!");
		}
	}

	@Override
	public <T> T post(T objeto) throws Exception {
		HttpClient client = null;
		HttpPost request = null;
		HttpResponse response = null;
		try {
			client = HttpClientBuilder.create().build();

			request = new HttpPost(this.endPoint + this.resource);
			this.configureHeader(request);
			StringEntity objetoJson = new StringEntity(GSON.toJson(objeto), ContentType.APPLICATION_JSON);
			request.setEntity(objetoJson);

			response = client.execute(request);

			return processarRetorno(response, objeto.getClass());

		} catch (Exception e) {
			throw new BusinessException("Falha ao fazer a requisição ao servidor!");
		}
	}

	@Override
	public <V, T> List<V> post(T objeto, List<V> type) throws Exception {
		Reader reader = null;
		List<V> colecao = null;

		HttpClient client = null;
		HttpPost request = null;
		HttpResponse response = null;

		try {
			client = HttpClientBuilder.create().build();

			request = new HttpPost(this.endPoint + this.resource);
			this.configureHeader(request);

			StringEntity objetoJson = new StringEntity(GSON.toJson(objeto), ContentType.APPLICATION_JSON);
			request.setEntity(objetoJson);

			response = client.execute(request);

			int codigoRetorno = response.getStatusLine().getStatusCode();
			if (OK == codigoRetorno) {
				reader = getReader(response.getEntity().getContent());
				colecao = GSON.fromJson(reader, new ProxyTokenType<V>().getType());
			} else {
				return processarRetorno(response, type.getClass());
			}

		} catch (IOException e) {
			tratarException(e);
		} finally {
			finalizar(reader, request);
		}

		return colecao;
	}

	@Override
	public <V> List<V> post(File objeto, List<V> type) throws Exception {
		Reader reader = null;
		List<V> colecao = null;

		HttpClient client = null;
		HttpPost request = null;
		HttpResponse response = null;

		try {
			client = HttpClientBuilder.create().build();

			request = new HttpPost(this.endPoint + this.resource);
			this.configureHeaderFile(request);

			FileEntity objetoJson = new FileEntity(objeto, ContentType.MULTIPART_FORM_DATA);
			request.setEntity(objetoJson);

			response = client.execute(request);

			int codigoRetorno = response.getStatusLine().getStatusCode();
			if (OK == codigoRetorno) {
				reader = getReader(response.getEntity().getContent());
				colecao = GSON.fromJson(reader, new ProxyTokenType<V>().getType());
			} else {
				return processarRetorno(response, type.getClass());
			}

		} catch (IOException e) {
			tratarException(e);
		} finally {
			finalizar(reader, request);
		}

		return colecao;
	}

	@Override
	public <T> T put(T objeto) throws Exception {
		HttpClient client = null;
		HttpPut request = null;
		HttpResponse response = null;

		try {
			client = HttpClientBuilder.create().build();

			request = new HttpPut(this.endPoint + this.resource);
			this.configureHeader(request);

			StringEntity objetoJson = new StringEntity(GSON.toJson(objeto), ContentType.APPLICATION_JSON);
			request.setEntity(objetoJson);

			response = client.execute(request);
			return processarRetorno(response, objeto.getClass());

		} catch (IOException e) {
			tratarException(e);
		} finally {
			finalizar(request);
		}
		return null;
	}

	@Override
	public <V, T> List<V> put(T objeto, List<V> type) throws Exception {
		Reader reader = null;
		List<V> colecao = null;

		HttpClient client = null;
		HttpPut request = null;
		HttpResponse response = null;

		try {
			client = HttpClientBuilder.create().build();

			request = new HttpPut(this.endPoint + this.resource);
			this.configureHeader(request);

			StringEntity objetoJson = new StringEntity(GSON.toJson(objeto), ContentType.APPLICATION_JSON);
			request.setEntity(objetoJson);

			response = client.execute(request);

			int codigoRetorno = response.getStatusLine().getStatusCode();
			if (OK == codigoRetorno) {
				reader = getReader(response.getEntity().getContent());
				colecao = GSON.fromJson(reader, new ProxyTokenType<V>().getType());
			} else {
				return processarRetorno(response, type.getClass());
			}

		} catch (IOException e) {
			tratarException(e);
		} finally {
			finalizar(reader, request);
		}

		return colecao;
	}

	@Override
	public <T> T put(T objeto, String pathParam) throws Exception {
		StringBuilder newURL = new StringBuilder(this.resource);
		if (pathParam != null) {
			newURL.append("/").append(pathParam);
		}
		setResource(newURL.toString());

		return put(objeto);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <V, T> V put(T objeto, String pathParam, Class<V> type) throws Exception {
		StringBuilder newURL = new StringBuilder(this.resource);
		if (pathParam != null) {
			newURL.append("/").append(pathParam);
		}
		setResource(newURL.toString());

		return (V) put(objeto, type.getClass());
	}

	@Override
	public <V, T> V put(T objeto, Class<V> type) throws Exception {
		HttpClient client = null;
		HttpPut request = null;
		HttpResponse response = null;

		try {
			client = HttpClientBuilder.create().build();

			request = new HttpPut(this.endPoint + this.resource);
			this.configureHeader(request);

			StringEntity objetoJson = new StringEntity(GSON.toJson(objeto), ContentType.APPLICATION_JSON);
			request.setEntity(objetoJson);

			response = client.execute(request);
			return processarRetorno(response, type.getClass());

		} catch (IOException e) {
			tratarException(e);
		} finally {
			finalizar(request);
		}
		return null;
	}
	
	@Override
	public <T> T put(List<String> pathParam, T objeto) throws Exception {
		StringBuilder newURL = new StringBuilder(this.resource);
		if (!Util.isNullOrEmpty(pathParam)) {
			for (String vlParametro : pathParam) {
				newURL.append("/").append(vlParametro);
			}
		}
		setResource(newURL.toString());

		return put(objeto);
	}

	@Override
	public <T> T delete() throws Exception {
		HttpClient client = null;
		HttpDelete request = null;
		HttpResponse response = null;

		try {
			client = HttpClientBuilder.create().build();

			request = new HttpDelete(this.endPoint + this.resource);
			this.configureHeader(request);

			response = client.execute(request);
			return processarRetorno(response, Boolean.class);

		} catch (IOException e) {
			tratarException(e);
		} finally {
			finalizar(request);
		}
		return null;
	}

	@Override
	public <T> T delete(T objeto) throws Exception {
		HttpClient client = null;
		HttpDelete request = null;
		HttpResponse response = null;

		try {
			client = HttpClientBuilder.create().build();

			request = new HttpDelete(this.endPoint + this.resource);
			this.configureHeader(request);

			response = client.execute(request);
			return processarRetorno(response, objeto.getClass());

		} catch (IOException e) {
			tratarException(e);
		} finally {
			finalizar(request);
		}
		return null;
	}
	
	@Override
	public <V, T> V delete(T objeto, Class<V> type) throws Exception {
		HttpClient client = null;
		HttpDelete request = null;
		HttpResponse response = null;

		try {
			client = HttpClientBuilder.create().build();

			request = new HttpDelete(this.endPoint + this.resource);
			this.configureHeader(request);

			response = client.execute(request);
			return processarRetorno(response, type.getClass());

		} catch (IOException e) {
			tratarException(e);
		} finally {
			finalizar(request);
		}
		return null;
	}

	@Override
	public <T> T delete(String pathParam) throws Exception {
		StringBuilder newURL = new StringBuilder(this.resource);
		if (pathParam != null) {
			newURL.append("/").append(pathParam);
		}
		setResource(newURL.toString());

		return delete();
	}

	@Override
	public <T> T delete(List<String> pathParam) throws Exception {
		StringBuilder newURL = new StringBuilder(this.resource);
		if (!Util.isNullOrEmpty(pathParam)) {
			for (String vlParametro : pathParam) {
				newURL.append("/").append(vlParametro);
			}
		}
		setResource(newURL.toString());

		return delete();
	}

	private <T> T processarRetorno(HttpResponse response, Type classType) throws IOException, Exception {
		T retorno = null;
		int codigoRetorno = response.getStatusLine().getStatusCode();
		if (isReturnValid(codigoRetorno)) {
			retorno = GSON.fromJson(getReader(response.getEntity().getContent()), classType);
		}
		return retorno;
	}

	private boolean isReturnValid(int codigoRetorno) {
		return (OK == codigoRetorno) || (CREATED == codigoRetorno);
	}

	private void configureHeader(HttpMessage request) throws Exception {
		request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
		request.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
		String applicationToken = Objects.nonNull(jwt) ? jwt.getTokenValue() : null;
		if (Objects.nonNull(applicationToken)) {
			request.addHeader(AUTORIZATION, BEARER.concat(applicationToken));
		}
	}

	private void configureHeaderFile(HttpMessage request) throws Exception {
		request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA);
		request.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
		String applicationToken = Objects.nonNull(jwt) ? jwt.getTokenValue() : null;
		if (Objects.nonNull(applicationToken)) {
			request.addHeader(AUTORIZATION, BEARER.concat(applicationToken));
		}
	}

	private static void tratarException(IOException e) throws Exception {
		String message = e.getMessage() != null ? e.getMessage() : "";
		throw new BusinessException(message, e);
	}

	private static void finalizar(Reader reader, HttpRequestBase request) {
		finalizar(request);
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				log.error("Erro interno no servidor!", e);
			}
		}
	}

	private static void finalizar(HttpRequestBase request) {
		if (request != null) {
			request.reset();
		}
	}

	protected static DateSerializer getDateSerializer() {
		return new DateSerializer();
	}

	protected static DateDeserializer getDateDeserializer() {
		return new DateDeserializer();
	}

	protected Reader getReader(InputStream content) {
		return new InputStreamReader(content, StandardCharsets.UTF_8);
	}

	private static class ProxyTokenType<T> extends TypeToken<ArrayList<T>> {
	}
}
