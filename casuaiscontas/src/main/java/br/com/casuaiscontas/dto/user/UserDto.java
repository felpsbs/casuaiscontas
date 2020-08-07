package br.com.casuaiscontas.dto.user;

import javax.validation.constraints.Size;

import org.springframework.util.StringUtils;

import br.com.casuaiscontas.validation.constraint.ConfirmationAttribute;

@ConfirmationAttribute(attribute = "password", confirmationAttribute = "confirmPassword", message = "Senhas não conferem")
public class UserDto {

	private String email;
	private String verificationCod;

	@Size(min = 6)
	private String password;

	private String confirmPassword;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVerificationCod() {
		return verificationCod;
	}

	public void setVerificationCod(String verificationCod) {
		this.verificationCod = verificationCod;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public boolean isAuthenticated() {
		return !StringUtils.isEmpty(email); 
	}

}
