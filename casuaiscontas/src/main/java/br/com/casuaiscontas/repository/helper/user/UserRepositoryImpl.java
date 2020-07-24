package br.com.casuaiscontas.repository.helper.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.casuaiscontas.model.Address;
import br.com.casuaiscontas.model.User;
import br.com.casuaiscontas.repository.filter.UserFilter;
import br.com.casuaiscontas.repository.pagination.PaginationUtil;

public class UserRepositoryImpl implements UserQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginationUtil paginationUtil;	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<User> filter(UserFilter userFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		
		Root<User> userEntity = query.from(User.class);
		userEntity.join("groups", JoinType.LEFT);
		
		Predicate[] filters = addFilter(userFilter, userEntity);
		
		query.select(userEntity).distinct(true).where(filters);
		
		TypedQuery<User> typedQuery = (TypedQuery<User>) paginationUtil.setOrder(query, userEntity, pageable);
		typedQuery = (TypedQuery<User>) paginationUtil.setInterval(typedQuery, pageable);
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, total(userFilter));
	}

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
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);

		Root<User> root = query.from(User.class);

		root.join("groups", JoinType.LEFT);
		Join<User, Address> address = root.join("address", JoinType.LEFT);
		Join<Address, User> city = address.join("city", JoinType.LEFT);
		city.join("state", JoinType.LEFT);

		query.select(root).distinct(true);
		query.where(builder.equal(root.get("id"), id));

		TypedQuery<User> typedQuery = manager.createQuery(query);

		return typedQuery.getResultList().stream().findFirst();
	}
	
	private Long total(UserFilter userFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<User> userEntity = query.from(User.class);
		
		query.select(builder.count(userEntity));
		query.where(addFilter(userFilter, userEntity));
		
		return manager.createQuery(query).getSingleResult();
	}
	
	private Predicate[] addFilter(UserFilter filter, Root<User> userEntity) {		
		List<Predicate> predicateList = new ArrayList<>();
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		if(filter != null) {
			if(!StringUtils.isEmpty(filter.getName())) {
				predicateList.add(builder.like(userEntity.get("name"), "%" + filter.getName() + "%"));
			}
			
			if(!StringUtils.isEmpty(filter.getEmail())) {
				predicateList.add(builder.like(userEntity.get("email"), filter.getEmail()));
			}
			
			if(!StringUtils.isEmpty(filter.getCpf())) {
				predicateList.add(builder.like(userEntity.get("cpf"), filter.getCpf()));
			}
			
			if(filter.getGroups() != null && !filter.getGroups().isEmpty()) {
				filter.getGroups().forEach(g -> predicateList.add(builder.isMember(g, userEntity.get("groups"))));				
			}
		}
		
		return predicateList.toArray(new Predicate[predicateList.size()]);
	}

}
