package br.com.casuaiscontas.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "grupo")
public class Group extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ManyToMany
	@JoinTable(name = "grupo_permition", joinColumns = @JoinColumn(name = "id_grupo"), inverseJoinColumns = @JoinColumn(name = "id_permition"))
	private Set<Permition> permitions;

	public Set<Permition> getPermitions() {
		return permitions;
	}

}
