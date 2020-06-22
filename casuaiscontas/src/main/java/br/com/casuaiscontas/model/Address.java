package br.com.casuaiscontas.model; 

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String cep;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_city")
	private City city;

	@Transient
	private State state;

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

}
