package br.com.casuaiscontas.model;

public enum BillStatus {

	PAYED("Paga"), 
	NOT_PAYED("Não paga");

	private String description;

	BillStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
