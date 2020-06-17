package br.com.casuaiscontas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.casuaiscontas.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
}
