package br.com.casuaiscontas.repository.helper.city;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.casuaiscontas.model.City;
import br.com.casuaiscontas.repository.filter.CityFilter;

public interface CityQueries {

	Page<City> filter(CityFilter cityFilter, Pageable pageable);
	
	Optional<City> findCityWithState(Long id);
}
