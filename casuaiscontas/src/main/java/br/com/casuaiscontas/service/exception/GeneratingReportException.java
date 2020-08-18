package br.com.casuaiscontas.service.exception;

public class GeneratingReportException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GeneratingReportException(String msg) {
		super(msg);
	}
}