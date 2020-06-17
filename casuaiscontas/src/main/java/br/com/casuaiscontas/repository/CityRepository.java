package br.com.casuaiscontas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.casuaiscontas.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

	List<City> findByStateId(Long stateId);
}
