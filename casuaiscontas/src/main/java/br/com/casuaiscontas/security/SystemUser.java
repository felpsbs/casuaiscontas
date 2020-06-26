package br.com.casuaiscontas.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SystemUser extends User {

	private static final long serialVersionUID = 1L;

	private br.com.casuaiscontas.model.User user;

	public SystemUser(br.com.casuaiscontas.model.User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getEmail(), user.getPassword(), authorities);
		this.user = user;
	}

	public br.com.casuaiscontas.model.User getUser() {
		return user;
	}

}
