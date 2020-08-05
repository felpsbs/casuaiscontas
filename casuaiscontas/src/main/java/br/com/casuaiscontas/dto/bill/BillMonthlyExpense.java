package br.com.casuaiscontas.dto.bill;

import java.math.BigDecimal;

public class BillMonthlyExpense {

	private String month;
	private BigDecimal expense;

	public BillMonthlyExpense() {

	}

	public BillMonthlyExpense(BigDecimal expense, String month) {
		this.month = month;
		this.expense = expense;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public BigDecimal getExpense() {
		return expense;
	}

	public void setExpense(BigDecimal expense) {
		this.expense = expense;
	}

}
