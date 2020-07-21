package br.com.casuaiscontas.repository.filter;

import java.util.Set;

import br.com.casuaiscontas.model.Group;

public class UserFilter {

	private String name;
	private String email;
	private String cpf;
	private Set<Group> groups;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

}
