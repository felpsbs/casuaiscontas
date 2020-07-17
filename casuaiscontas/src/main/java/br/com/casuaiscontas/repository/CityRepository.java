package br.com.casuaiscontas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.casuaiscontas.model.City;
import br.com.casuaiscontas.model.State;
import br.com.casuaiscontas.repository.helper.city.CityQueries;

@Repository
public interface CityRepository extends JpaRepository<City, Long>, CityQueries {

	List<City> findByStateId(Long stateId);

	Optional<City> findByNameAndState(String name, State state);
	
}
