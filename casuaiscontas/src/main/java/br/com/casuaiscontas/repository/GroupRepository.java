package br.com.casuaiscontas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.casuaiscontas.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

}
