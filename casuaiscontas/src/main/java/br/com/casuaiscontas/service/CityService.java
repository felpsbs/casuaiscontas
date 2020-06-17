package br.com.casuaiscontas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.casuaiscontas.model.City;
import br.com.casuaiscontas.repository.CityRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository repository;
	
	public List<City> findByState(Long stateId) {
		return repository.findByStateId(stateId);
	}
}
  