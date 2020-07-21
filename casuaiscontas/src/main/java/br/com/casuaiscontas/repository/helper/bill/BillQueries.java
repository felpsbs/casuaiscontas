package br.com.casuaiscontas.repository.helper.bill;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.casuaiscontas.model.Bill;
import br.com.casuaiscontas.model.User;
import br.com.casuaiscontas.repository.filter.BillFilter;

public interface BillQueries {
	
	Page<Bill> filter(BillFilter billFilter, Pageable pageable, User user);
	
	Optional<Bill> findBillWithUser(Long id);

}
