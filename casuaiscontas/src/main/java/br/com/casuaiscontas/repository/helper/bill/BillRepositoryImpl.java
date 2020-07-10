package br.com.casuaiscontas.repository.helper.bill;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import br.com.casuaiscontas.model.Bill;

public class BillRepositoryImpl implements BillQueries {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Optional<Bill> findBillWithUser(Long id) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Bill> query = criteriaBuilder.createQuery(Bill.class);

		Root<Bill> root = query.from(Bill.class);

		root.join("user", JoinType.LEFT);		

		query.select(root).distinct(true);
		query.where(criteriaBuilder.equal(root.get("id"), id));

		TypedQuery<Bill> typedQuery = manager.createQuery(query);

		return typedQuery.getResultList().stream().findFirst();
	}

}
