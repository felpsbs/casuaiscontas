package br.com.casuaiscontas.service.exception;

public class CityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CityNotFoundException(String msg) {
		super(msg);
	}
}

