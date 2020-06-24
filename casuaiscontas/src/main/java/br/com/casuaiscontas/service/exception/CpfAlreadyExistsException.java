package br.com.casuaiscontas.service.exception;

public class CpfAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CpfAlreadyExistsException(String msg) {
		super(msg);
	}
}
