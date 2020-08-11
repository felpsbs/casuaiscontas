package br.com.casuaiscontas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.casuaiscontas.dto.user.UserDto;
import br.com.casuaiscontas.model.LegalEntity;
import br.com.casuaiscontas.model.User;
import br.com.casuaiscontas.model.UserStatus;
import br.com.casuaiscontas.repository.UserRepository;
import br.com.casuaiscontas.repository.filter.UserFilter;
import br.com.casuaiscontas.service.event.user.SaveUserEvent;
import br.com.casuaiscontas.service.exception.CpfAlreadyExistsException;
import br.com.casuaiscontas.service.exception.EmailAlreadyExistsException;
import br.com.casuaiscontas.service.exception.UserNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@Transactional
	public void save(User user) {
		User existentUser = isPresent(user);

		if (!user.isNew()) {
			user.setActive(existentUser.getActive());
			user.setPassword(existentUser.getPassword());
		} else {
			this.encodePassword(user);
		}

		user.setConfirmPassword(user.getPassword());				
		publisher.publishEvent(new SaveUserEvent(repository.saveAndFlush(user)));
	}
	
	@Transactional
	public void updatePassword(Long id, UserDto userDto) {
		User user = repository.findById(id).get();		
		user.setPassword(userDto.getPassword());
		this.encodePassword(user);			
		repository.saveAndFlush(user);
	}
	
	@Transactional
	public void updateStatus(Long[] ids, UserStatus userStatus) {
		userStatus.update(ids, this);
	}
	
	@Transactional
	public void confirmRegister(Long id) {
		User user = repository.findById(id).get();
		user.setActive(Boolean.TRUE);
		repository.saveAndFlush(user);
	}
	
	public void updateStatus(Long[] ids, boolean status) {
		repository.findByIdIn(ids).forEach(u -> u.setActive(status));
	}
	
	public User findUserWithGroups(Long id) {
		return repository.findUserWithGroups(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));		
	}
	
	public User findByEmail(String email) {
		return repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
	}

	public Optional<User> findByEmailAndActive(String email) {
		return repository.byEmailAndActive(email);
	}

	public Page<User> filter(UserFilter userFilter, Pageable pageable) {
		return repository.filter(userFilter, pageable);
	}
		
	public List<String> findPermitions(User user) {
		return repository.findPermitions(user);
	}		
	
	private void encodePassword(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
	}

	private User isPresent(User user) {
		Optional<User> existentUser = repository.findByCpf(LegalEntity.removeFormatting(user.getCpf()));
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
