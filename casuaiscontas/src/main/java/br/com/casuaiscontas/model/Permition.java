package br.com.casuaiscontas.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "permition")
public class Permition extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

}
