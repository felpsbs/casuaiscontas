package br.com.casuaiscontas.dto.bill;

import java.math.BigDecimal;

public class BillDto {

	private BigDecimal paidExpense;
	private BigDecimal notPaidExpense;
	
	public BillDto() {
		
	}
	
	public BillDto(BigDecimal paid, BigDecimal notPaid) {
		this.paidExpense = paid;
		this.notPaidExpense = notPaid;
	}

	public BigDecimal getPaid() {
		return paidExpense;
	}

	public void setPaid(BigDecimal paid) {
		this.paidExpense = paid;
	}

	public BigDecimal getNotPaid() {
		return notPaidExpense;
	}

	public void setNotPaid(BigDecimal notPaid) {
		this.notPaidExpense = notPaid;
	}

}
