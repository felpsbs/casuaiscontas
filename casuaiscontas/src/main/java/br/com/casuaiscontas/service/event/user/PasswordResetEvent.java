package br.com.casuaiscontas.service.event.user;

import org.springframework.util.Base64Utils;

public class PasswordResetEvent {

	private String email;
	private String cod;
	
	public PasswordResetEvent(String email, String cod) {
		this.email = email;
		this.cod = cod;
	}

	public String getEmail() {
		return email;
	}

	public String getCod() {
		return cod;
	}
	
	public String getEncodedEmail() {
		return Base64Utils.encodeToString(this.email.getBytes());
	}
	
}
