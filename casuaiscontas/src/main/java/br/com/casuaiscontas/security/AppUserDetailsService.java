package br.com.casuaiscontas.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.casuaiscontas.model.User;
import br.com.casuaiscontas.service.UserService;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService service;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> userOptional = service.findByEmailAndActive(email);
		User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("E-mail/Senha inválidos"));
		return new SystemUser(user, getPermitions(user));
	}

	private Collection<? extends GrantedAuthority> getPermitions(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		List<String> permitions = service.findPermitions(user);
		permitions.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.toUpperCase())));
		return authorities;
	}

}
