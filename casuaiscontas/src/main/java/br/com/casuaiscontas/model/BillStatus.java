package br.com.casuaiscontas.model;

public enum BillStatus {

	PAID("Paga"), 
	NOT_PAID("Não paga");

	private String description;

	BillStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
