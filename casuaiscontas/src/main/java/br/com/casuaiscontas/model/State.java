package br.com.casuaiscontas.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "state")
public class State extends BaseModel {
    
	private static final long serialVersionUID = 1L;
	
	private String uf;

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

}
