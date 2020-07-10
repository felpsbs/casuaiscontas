package br.com.casuaiscontas.service.exception;

public class BillNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BillNotFoundException(String msg) {
		super(msg);
	}
}
