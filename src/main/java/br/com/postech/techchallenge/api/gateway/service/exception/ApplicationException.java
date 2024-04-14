package br.com.postech.techchallenge.api.gateway.service.exception;

/**
 * Classe que representa as Exceptions lançadas pela aplicação
 * 
 * @author diogsant
 *
 */
public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ApplicationException(String mensagem) {
		super(mensagem);
	}

	public ApplicationException(Throwable throwable) {
		super(throwable);
	}

	public ApplicationException(String mensagem, final Throwable throwable) {
		super(mensagem, throwable);
	}
}
