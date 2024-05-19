package br.com.postech.techchallenge.api.gateway.service.exception;

public class Falha {

	private int httpStatus;
	private String mensagem;

	public Falha() {
		super();
	}

	public Falha(int httpStatus, String mensagem) {
		super();
		this.httpStatus = httpStatus;
		this.mensagem = mensagem;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public String getMensagem() {
		return mensagem;
	}
}
