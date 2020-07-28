package br.com.casuaiscontas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.casuaiscontas.model.User;
import br.com.casuaiscontas.repository.helper.user.UserQueries;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserQueries {

	Optional<User> findByEmail(String email);
	
	Optional<User> findByCpf(String cpf);	
	
	List<User> findByIdIn(Long[] ids);
	
}
