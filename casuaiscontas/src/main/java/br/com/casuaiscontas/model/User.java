package br.com.casuaiscontas.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.br.CPF;

import br.com.casuaiscontas.validation.constraint.ConfirmationAttribute;

@ConfirmationAttribute(attribute = "password", confirmationAttribute = "confirmPassword", message = "Senhas não conferem")
@Entity
@Table(name = "user")
@DynamicUpdate
public class User extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Email
	@NotBlank
	@Column(unique = true)
	private String email;

	@Size(min = 6)
	private String password;

	@Transient
	private String confirmPassword;

	@NotBlank
	private String phone;

	@CPF
	private String cpf;

	@NotNull
	private LocalDate birthdate;

	private boolean active;

	@Column(name = "created_at", updatable = false)
	private LocalDate createdAt;

	@Column(name = "updated_at")
	private LocalDate updatedAt;

	@Valid
	@Embedded
	private Address address;

	@ManyToMany
	@JoinTable(name = "user_grupo", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_grupo"))
	private Set<Group> groups;

	@PrePersist
	public void onSave() {
		active = false;
		createdAt = LocalDate.now();
	}

	@PreUpdate
	public void onUpdate() {
		confirmPassword = password;
		updatedAt = LocalDate.now();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

}
