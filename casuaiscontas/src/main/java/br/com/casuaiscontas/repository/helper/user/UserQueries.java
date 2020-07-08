package br.com.casuaiscontas.repository.helper.user;

import java.util.List;
import java.util.Optional;

import br.com.casuaiscontas.model.User;

public interface UserQueries {

	List<String> findPermitions(User user);

	Optional<User> findUserWithGroups(Long id);	
	
	Optional<User> byEmailAndActive(String email);
	
}
