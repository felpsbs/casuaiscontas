package br.com.casuaiscontas.repository.helper.bill;

import java.math.BigDecimal;
import java.time.LocalDate;
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

import br.com.casuaiscontas.dto.bill.BillDto;
import br.com.casuaiscontas.dto.bill.BillMonthlyExpense;
import br.com.casuaiscontas.model.Bill;
import br.com.casuaiscontas.model.User;
import br.com.casuaiscontas.repository.filter.BillFilter;
import br.com.casuaiscontas.repository.pagination.PaginationUtil;

public class BillRepositoryImpl implements BillQueries {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginationUtil paginationUtil;
	
	@Override
	public Optional<BillDto> findMonthExpense(Long userId) {
		return manager.createNamedQuery("Bill.findMonthExpense", BillDto.class).setParameter("userId", userId)			
			.getResultList().stream().findFirst();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BillMonthlyExpense> findMonthlyExpense(Long userId) {
		return this.filterMonths(manager.createNamedQuery("Bill.findMonthlyExpense").setParameter("userId", userId).getResultList());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Bill> filter(BillFilter billFilter, Pageable pageable, User user) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Bill> query = builder.createQuery(Bill.class);
		
		Root<Bill> billEntity = query.from(Bill.class);		
		Predicate[] filters = this.addFilter(billFilter, billEntity, user);
		query.select(billEntity).where(filters);		
		
		TypedQuery<Bill> typedQuery = (TypedQuery<Bill>) paginationUtil.setOrder(query, billEntity, pageable);
		typedQuery = (TypedQuery<Bill>) paginationUtil.setInterval(typedQuery, pageable);
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, this.total(billFilter, user));
	}	

	@Override
	public Optional<Bill> findBillWithUser(Long id) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Bill> query = builder.createQuery(Bill.class);

		Root<Bill> root = query.from(Bill.class);
		root.join("user", JoinType.LEFT);		

		query.select(root).distinct(true);
		query.where(builder.equal(root.get("id"), id));

		TypedQuery<Bill> typedQuery = manager.createQuery(query);

		return typedQuery.getResultList().stream().findFirst();
	}
	
	private List<BillMonthlyExpense> filterMonths(List<BillMonthlyExpense> bills) {
		LocalDate now = LocalDate.now();
		
		for (int i = 1; i <= 6; i++) {
			String idealMonth = String.format("%02d/%d", now.getMonthValue(), now.getYear());
		
			boolean isPresent = bills.stream().filter(b -> b.getMonth().equals(idealMonth)).findAny().isPresent();
			if(!isPresent) {
				bills.add(i - 1, new BillMonthlyExpense(BigDecimal.ZERO, idealMonth));	
			}
			
			now = now.minusMonths(1);
		}

		if(bills.size() > 6) {
			bills.remove(bills.size() - 1);
		}
		
		return bills;
	}
	
	private Long total(BillFilter billFilter, User user) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Bill> billEntity = query.from(Bill.class);
		
		query.select(builder.count(billEntity));
		query.where(addFilter(billFilter, billEntity, user));
		
		return manager.createQuery(query).getSingleResult();
	}
	
	private Predicate[] addFilter(BillFilter filter, Root<Bill> billEntity, User user) {
		billEntity.join("user", JoinType.LEFT);
		
		List<Predicate> predicateList = new ArrayList<>();
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		predicateList.add(builder.equal(billEntity.get("user"), user));
		
		if(filter != null) {
			if(!StringUtils.isEmpty(filter.getName())) {
				predicateList.add(builder.like(billEntity.get("name"), "%" + filter.getName() + "%"));
			}
			
			if(filter.getStatus() != null) {				
				predicateList.add(builder.equal(billEntity.get("status"), filter.getStatus()));
			}
			
			if(filter.getDueDate() != null) {
				predicateList.add(builder.equal(billEntity.get("dueDate"), filter.getDueDate()));
			}
			
			if(filter.getPriceFrom() != null) {
				predicateList.add(builder.gt(billEntity.get("price"), filter.getPriceFrom()));
			}
			
			if(filter.getPriceTo() != null) {
				predicateList.add(builder.lt(billEntity.get("price"), filter.getPriceTo()));
			}
			
		}
		
		return predicateList.toArray(new Predicate[predicateList.size()]);
	}

}
