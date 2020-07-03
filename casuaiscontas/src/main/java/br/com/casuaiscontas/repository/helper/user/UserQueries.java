package br.com.casuaiscontas.repository.helper.user;

import java.util.List;
import java.util.Optional;

import br.com.casuaiscontas.model.User;

public interface UserQueries {

	User findUserWithGroups(Long id);
	
	List<String> findPermitions(User user);
	
	Optional<User> byEmailAndActive(String email);
	
}
