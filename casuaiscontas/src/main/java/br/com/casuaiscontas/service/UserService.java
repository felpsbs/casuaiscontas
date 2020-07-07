package br.com.casuaiscontas.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public void save(User user) {
		User existentUser = isPresent(user);

		if (!user.isNew()) {
			user.setActive(existentUser.getActive());
			user.setPassword(existentUser.getPassword());
		} else {
			encodePassword(user);
		}

		user.setConfirmPassword(user.getPassword());

		// enviar email

		repository.save(user);
	}
	
	public User findUserWithGroups(Long id) {
		return repository.findUserWithGroups(id);
	}

	private void encodePassword(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
	}

	private User isPresent(User user) {
		Optional<User> existentUser = repository.findByCpf(user.getCpf());
		if (existentUser.isPresent() && !existentUser.get().equals(user)) {
			throw new CpfAlreadyExistsException("CPF já cadastrado");
		}

		existentUser = repository.findByEmail(user.getEmail());
		if (existentUser.isPresent() && !existentUser.get().equals(user)) {
			throw new EmailAlreadyExistsException("E-mail já cadastrado");
		}

		return existentUser.orElse(user);
	}

}
