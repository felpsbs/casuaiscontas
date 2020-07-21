package br.com.casuaiscontas.repository.filter;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.casuaiscontas.model.BillStatus;

public class BillFilter {

	private String name;
	private LocalDate dueDate;
	private BillStatus status;
	private BigDecimal priceFrom;
	private BigDecimal priceTo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public BillStatus getStatus() {
		return status;
	}

	public void setStatus(BillStatus status) {
		this.status = status;
	}

	public BigDecimal getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(BigDecimal priceFrom) {
		this.priceFrom = priceFrom;
	}

	public BigDecimal getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(BigDecimal priceTo) {
		this.priceTo = priceTo;
	}

}
