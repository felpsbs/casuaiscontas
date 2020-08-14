package br.com.casuaiscontas.dto.user;

import javax.validation.constraints.Size;

import org.springframework.util.StringUtils;

import br.com.casuaiscontas.validation.constraint.ConfirmationAttribute;

@ConfirmationAttribute(attribute = "password", confirmationAttribute = "confirmPassword", message = "Senhas não conferem")
public class UserDto {

	private String email;
	private String cod;

	@Size(min = 6)
	private String password;

	private String confirmPassword;

	public UserDto() {

	}

	public UserDto(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
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

	public boolean isCodEquals(String cod) {
		return this.cod.equals(cod);
	}

}
