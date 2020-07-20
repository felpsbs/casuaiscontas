package br.com.casuaiscontas.repository.pagination;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginationUtil {

	@PersistenceContext
	private EntityManager manager;

	public TypedQuery<?> setInterval(TypedQuery<?> query, Pageable pageable) {
		int currentPage = pageable.getPageNumber();
		int totalRecordPerPage = pageable.getPageSize();
		int firstRecord = currentPage * totalRecordPerPage;

		query.setFirstResult(firstRecord);
		query.setMaxResults(totalRecordPerPage);

		return query;
	}

	public TypedQuery<?> setOrder(CriteriaQuery<?> query, Root<?> fromEntity, Pageable pageable) {
		Sort sort = pageable.getSort();

		if (sort != null && sort.isSorted()) {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			Sort.Order sortOrder = sort.iterator().next();
			String propString = sortOrder.getProperty();

			Order order = sortOrder.isAscending() ? builder.asc(fromEntity.get(propString))
					: builder.desc(fromEntity.get(propString));

			query.orderBy(order);
		}

		return manager.createQuery(query);
	}
}
