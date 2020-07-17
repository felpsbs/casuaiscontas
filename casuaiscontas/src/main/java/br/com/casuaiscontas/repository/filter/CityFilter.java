package br.com.casuaiscontas.repository.filter;

import br.com.casuaiscontas.model.State;

public class CityFilter {

	private State state;
	private String name;

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
