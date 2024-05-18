package br.com.postech.techchallenge.api.gateway.service.http;

import java.io.File;
import java.util.List;

public interface HttpAdapter {
	<T> T get(Class<T> tipo) throws Exception;

	<T> T get(Class<T> tipo, String pathParam) throws Exception;

	<T> T get(Class<T> tipo, List<String> pathParam) throws Exception;

	<T> List<T> get(List<T> type) throws Exception;
	
	<T> List<T> get(List<T> type, String pathParam) throws Exception;

	<V, T> V post(T objeto, Class<V> type) throws Exception;

	<T> T post(T objeto) throws Exception;

	<V, T> List<V> post(T objeto, List<V> type) throws Exception;

	<V> List<V> post(File objeto, List<V> type) throws Exception;

	<T> T put(T objeto) throws Exception;

	<V, T> List<V> put(T objeto, List<V> type) throws Exception;

	<T> T put(T objeto, String pathParam) throws Exception;

	<T> T put(List<String> pathParam, T objeto) throws Exception;
	
	<V, T> V put(T objeto, Class<V> type) throws Exception;
	
	<V, T> V put(T objeto, String pathParam, Class<V> type) throws Exception;

	<T> T delete() throws Exception;

	<T> T delete(T objeto) throws Exception;
	
	<V, T> V delete(T objeto, Class<V> type) throws Exception;

	<T> T delete(String pathParam) throws Exception;

	<T> T delete(List<String> pathParam) throws Exception;
}
