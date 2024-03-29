package br.com.casuaiscontas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.casuaiscontas.dto.bill.BillDto;
import br.com.casuaiscontas.dto.bill.BillMonthlyExpense;
import br.com.casuaiscontas.model.Bill;
import br.com.casuaiscontas.model.User;
import br.com.casuaiscontas.repository.BillRepository;
import br.com.casuaiscontas.repository.filter.BillFilter;
import br.com.casuaiscontas.security.SystemUser;
import br.com.casuaiscontas.service.exception.BillNotFoundException;

@Service
public class BillService {

	@Autowired
	private BillRepository repository;

	@Transactional
	public void save(Bill bill, SystemUser systemUser) {
		if (!bill.isNew()) {
			Bill existentBill = findBillWithUser(bill.getId());
			bill.setUser(existentBill.getUser());
		} else {
			bill.setUser(systemUser.getUser());
		}

		repository.save(bill);
	}

	public Bill findBillWithUser(Long id) {
		return repository.findBillWithUser(id).orElseThrow(() -> new BillNotFoundException("Conta não encontrada"));
	}

	public Bill findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new BillNotFoundException("Conta não encontrada"));
	}

	public Page<Bill> filter(BillFilter billFilter, Pageable pageable, User user) {
		return repository.filter(billFilter, pageable, user);
	}

	@Transactional
	public void delete(Long id) {
		repository.deleteById(id);
	}

	public BillDto findMonthExpense(Long userId) {
		return repository.findMonthExpense(userId).orElse(new BillDto());
	}
	
	public List<BillMonthlyExpense> findMonthlyExpense(Long userId) {
		return repository.findMonthlyExpense(userId);
	}

}
