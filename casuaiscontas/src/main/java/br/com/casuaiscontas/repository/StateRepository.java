package br.com.casuaiscontas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.casuaiscontas.model.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

}
