package br.com.postech.techchallenge.api.gateway.service.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends ApplicationException {
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;

	public BusinessException(HttpStatus httpStatus, String mensagem) {
		super(mensagem);
		this.httpStatus = httpStatus;
	}

	public BusinessException(String mensagem) {
		super(mensagem);
	}

	public BusinessException(String mensagem, Throwable cause) {
		super(mensagem, cause);
	}

}
