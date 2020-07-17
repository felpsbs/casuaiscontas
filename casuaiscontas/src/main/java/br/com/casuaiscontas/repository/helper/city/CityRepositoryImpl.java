package br.com.casuaiscontas.repository.helper.city;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.casuaiscontas.model.City;
import br.com.casuaiscontas.repository.filter.CityFilter;
import br.com.casuaiscontas.repository.pagination.PaginationUtil;

public class CityRepositoryImpl implements CityQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginationUtil paginationUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<City> filter(CityFilter cityFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<City> query = builder.createQuery(City.class);
		Root<City> cityEntity = query.from(City.class);
		Predicate[] filters = addFilter(cityFilter, cityEntity);
		
		query.select(cityEntity).where(filters);
		
		TypedQuery<City> typedQuery = (TypedQuery<City>) paginationUtil.setOrder(query, cityEntity, pageable);
		typedQuery = (TypedQuery<City>) paginationUtil.setInterval(typedQuery, pageable);
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, total(cityFilter));
	}
	
	@Override
	public Optional<City> findCityWithState(Long id) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<City> query = criteriaBuilder.createQuery(City.class);

		Root<City> root = query.from(City.class);

		root.join("state", JoinType.LEFT);
		
		query.select(root).distinct(true);
		query.where(criteriaBuilder.equal(root.get("id"), id));

		TypedQuery<City> typedQuery = manager.createQuery(query);

		return typedQuery.getResultList().stream().findFirst();		
	}
	
	private Long total(CityFilter cityFilter) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<City> cityEntity = query.from(City.class);
		
		query.select(criteriaBuilder.count(cityEntity));
		query.where(addFilter(cityFilter, cityEntity));
		
		return manager.createQuery(query).getSingleResult();
	}

	private Predicate[] addFilter(CityFilter filter, Root<City> cityEntity) {
		List<Predicate> predicateList = new ArrayList<>();
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		if(filter != null) {
			if(!StringUtils.isEmpty(filter.getName())) {
				predicateList.add(builder.like(cityEntity.get("name"), "%" + filter.getName()));
			}
			
			if(filter.getState() != null) {
				predicateList.add(builder.equal(cityEntity.get("state"), filter.getState()));
			}
		}
		
		return predicateList.toArray(new Predicate[predicateList.size()]);
	}

}
