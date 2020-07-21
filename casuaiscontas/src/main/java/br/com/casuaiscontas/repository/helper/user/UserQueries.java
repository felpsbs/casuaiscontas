package br.com.casuaiscontas.repository.helper.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.casuaiscontas.model.User;
import br.com.casuaiscontas.repository.filter.UserFilter;

public interface UserQueries {

	List<String> findPermitions(User user);

	Optional<User> findUserWithGroups(Long id);	
	
	Optional<User> byEmailAndActive(String email);
	
	Page<User> filter(UserFilter userFilter, Pageable pageable);
	
}
