package br.com.casuaiscontas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.casuaiscontas.model.State;
import br.com.casuaiscontas.repository.StateRepository;

@Service
public class StateService {

	@Autowired
	private StateRepository repository;
	
	public List<State> findAll() {
		return repository.findAll();
	}
}
