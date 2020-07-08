package br.com.casuaiscontas.repository.helper.user;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import br.com.casuaiscontas.model.Address;
import br.com.casuaiscontas.model.City;
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
		return manager.createQuery(
				"select distinct p.name from User u inner join u.groups g inner join g.permitions p where u = :user",
				String.class).setParameter("user", user).getResultList();
	}

	@Override
	public Optional<User> findUserWithGroups(Long id) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);

		Root<User> root = query.from(User.class);

		root.join("groups", JoinType.LEFT);
		Join<User, Address> address = root.join("address", JoinType.LEFT);
		Join<Address, City> city = address.join("city", JoinType.LEFT);
		city.join("state", JoinType.LEFT);

		query.select(root).distinct(true);
		query.where(criteriaBuilder.equal(root.get("id"), id));

		TypedQuery<User> typedQuery = manager.createQuery(query);

		return typedQuery.getResultList().stream().findFirst();
	}

}
