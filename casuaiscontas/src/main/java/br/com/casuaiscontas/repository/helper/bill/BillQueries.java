package br.com.casuaiscontas.repository.helper.bill;

import java.util.Optional;

import br.com.casuaiscontas.model.Bill;

public interface BillQueries {
	
	Optional<Bill> findBillWithUser(Long id);

}
