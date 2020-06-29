package br.com.casuaiscontas.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.casuaiscontas.model.User;
import br.com.casuaiscontas.repository.UserRepository;
import br.com.casuaiscontas.service.exception.CpfAlreadyExistsException;
import br.com.casuaiscontas.service.exception.EmailAlreadyExistsException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void save(User user) {
		isPresent(user);
		// criptografar senha
		encodePassword(user);
		// enviar email
		repository.save(user);
	}

	public User findById(Long id) {
		// Lógica do id aqui
		return repository.findById(id).get();
	}

	private void encodePassword(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setConfirmPassword(user.getPassword());
	}

	private void isPresent(User user) {
		Optional<User> existentUser = repository.findByEmail(user.getEmail());
		if (existentUser.isPresent()) {
			throw new EmailAlreadyExistsException("E-mail já cadastrado");
		}

		existentUser = repository.findByCpf(user.getCpf());
		if (existentUser.isPresent()) {
			throw new CpfAlreadyExistsException("CPF já cadastrado");
		}
	}

}
