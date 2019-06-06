package com.frantishex.loyaltyprogramme.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseObject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public BaseObject() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}