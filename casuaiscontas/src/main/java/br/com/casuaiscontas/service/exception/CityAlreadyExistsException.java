package br.com.casuaiscontas.service.exception;

public class CityAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CityAlreadyExistsException(String msg) {
		super(msg);
	}
}
