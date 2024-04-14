package br.com.postech.techchallenge.api.gateway.service.exception;

public class ErrorDetails {

	private int httpStatus;
	private String titulo;
	private String mensagem;

	public ErrorDetails() {
		super();
	}

	public ErrorDetails(int httpStatus, String titulo, String mensagem) {
		super();
		this.httpStatus = httpStatus;
		this.titulo = titulo;
		this.mensagem = mensagem;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getMensagem() {
		return mensagem;
	}
}
