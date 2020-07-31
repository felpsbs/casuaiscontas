package br.com.casuaiscontas.dto;

import java.math.BigDecimal;

public class BillDto {

	private BigDecimal paid;
	private BigDecimal notPaid;
	
	public BillDto() {
		
	}
	
	public BillDto(BigDecimal paid, BigDecimal notPaid) {
		this.paid = paid;
		this.notPaid = notPaid;
	}

	public BigDecimal getPaid() {
		return paid;
	}

	public void setPaid(BigDecimal paid) {
		this.paid = paid;
	}

	public BigDecimal getNotPaid() {
		return notPaid;
	}

	public void setNotPaid(BigDecimal notPaid) {
		this.notPaid = notPaid;
	}

}
