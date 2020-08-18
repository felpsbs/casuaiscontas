package br.com.casuaiscontas.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class CustomReport {

	@NotNull
	private LocalDate dateFrom;
	
	@NotNull
	private LocalDate dateTo;

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

}
