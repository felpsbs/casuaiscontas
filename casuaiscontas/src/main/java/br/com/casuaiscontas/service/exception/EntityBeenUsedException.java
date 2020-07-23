package br.com.casuaiscontas.service.exception;

public class EntityBeenUsedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityBeenUsedException(String msg) {
		super(msg);
	}
}
