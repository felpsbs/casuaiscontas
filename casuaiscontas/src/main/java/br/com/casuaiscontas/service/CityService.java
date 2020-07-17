package br.com.casuaiscontas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.casuaiscontas.model.City;
import br.com.casuaiscontas.repository.CityRepository;
import br.com.casuaiscontas.repository.filter.CityFilter;
import br.com.casuaiscontas.service.exception.CityAlreadyExistsException;
import br.com.casuaiscontas.service.exception.CityNotFoundException;

@Service
public class CityService {

	@Autowired
	private CityRepository repository;
	
	@Transactional
	public void save(City city) {
		Optional<City> existentCity = repository.findByNameAndState(city.getName(), city.getState());
		if(existentCity.isPresent()) {
			throw new CityAlreadyExistsException("Cidade já cadastrada");
		}
			
		repository.save(city);		
	}
	
	public City findCityWithState(Long id) {
		return repository.findCityWithState(id).orElseThrow(() -> new CityNotFoundException("Cidade não encontrada"));
	}
		
	public List<City> findByState(Long stateId) {
		return repository.findByStateId(stateId);
	}
	
	public Page<City> filter(CityFilter cityFilter, Pageable pageable) {
		return repository.filter(cityFilter, pageable);
	}
}
  