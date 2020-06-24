package br.com.casuaiscontas.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.casuaiscontas.model.User;
import br.com.casuaiscontas.repository.UserRepository;
import br.com.casuaiscontas.service.exception.CpfAlreadyExistsException;
import br.com.casuaiscontas.service.exception.EmailAlreadyExistsException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public void save(User user) {
		isPresent(user);	
		// criptografar senha
		// enviar email
		repository.save(user);
	}

	private void isPresent(User user) {
		Optional<User> existentUser = repository.findByEmail(user.getEmail());				
		if(existentUser.isPresent()) {
			throw new EmailAlreadyExistsException("E-mail já cadastrado");
		}
		
		existentUser = repository.findByCpf(user.getCpf());
		if(existentUser.isPresent()) {
			throw new CpfAlreadyExistsException("CPF já cadastrado");
		}
	}
}
