package br.com.casuaiscontas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.casuaiscontas.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
