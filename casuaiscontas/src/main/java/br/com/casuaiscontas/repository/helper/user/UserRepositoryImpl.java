package br.com.casuaiscontas.repository.helper.user;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.casuaiscontas.model.User;

public class UserRepositoryImpl implements UserQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Optional<User> byEmailAndActive(String email) {
		return manager.createQuery("from User where lower(email) = lower(:email) and active = true", User.class)
				.setParameter("email", email).getResultList().stream().findFirst();
	}

	@Override
	public List<String> findPermitions(User user) {
		return manager
				.createQuery("select distinct p.name from User u inner join u.groups g inner join g.permitions p where u = :user", String.class)
				.setParameter("user", user)
				.getResultList();
	}

}
