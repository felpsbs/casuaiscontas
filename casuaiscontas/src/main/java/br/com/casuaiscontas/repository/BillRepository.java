package br.com.casuaiscontas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.casuaiscontas.model.Bill;
import br.com.casuaiscontas.repository.helper.bill.BillQueries;

public interface BillRepository  extends JpaRepository<Bill, Long>, BillQueries {

}
