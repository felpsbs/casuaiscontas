package br.com.casuaiscontas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.casuaiscontas.model.Group;
import br.com.casuaiscontas.repository.GroupRepository;

@Service
public class GroupService {

	@Autowired
	private GroupRepository repository;
	
	public List<Group> findAll() {
		return repository.findAll();
	}
}
