package br.com.casuaiscontas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.casuaiscontas.model.Bill;
import br.com.casuaiscontas.repository.BillRepository;
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

}
