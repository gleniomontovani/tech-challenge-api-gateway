package br.com.postech.techchallenge.api.gateway.service.exception;

public class NotFoundException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String mensagem) {
        super(mensagem);
    }

    public NotFoundException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }

}
