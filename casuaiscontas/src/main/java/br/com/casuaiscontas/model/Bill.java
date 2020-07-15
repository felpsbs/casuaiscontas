package br.com.casuaiscontas.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bill")
@DynamicUpdate
public class Bill extends BaseModel{

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String description;

	@NotNull
	@DecimalMin(value = "0.50")
	@DecimalMax(value = "9999999.99")
	private BigDecimal price;

	@NotNull
	@Min(value = 1)
	@Max(value = 9999)
	private Integer quantity;

	@NotNull
	@Column(name = "due_date")
	private LocalDate dueDate;

	@Enumerated(EnumType.STRING)
	private BillStatus status = BillStatus.NOT_PAYED;

	@Column(name = "created_at", updatable = false)
	private LocalDate createdAt;

	@Column(name = "updated_at")
	private LocalDate updatedAt;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user", nullable = false)
	private User user;

	@PrePersist
	public void onSave() {
		createdAt = LocalDate.now();
	}

	@PreUpdate
	public void onUpdate() {
		updatedAt = LocalDate.now();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public BillStatus getStatus() {
		return status;
	}

	public void setStatus(BillStatus status) {
		this.status = status;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BigDecimal getTotal() {		
		return price.multiply(new BigDecimal(quantity));
	}

}
