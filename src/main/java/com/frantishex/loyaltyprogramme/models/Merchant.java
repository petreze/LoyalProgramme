package com.frantishex.loyaltyprogramme.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = "MerchantByName", query = "SELECT m FROM Merchant m WHERE m.name = :merchantname"),

		@NamedQuery(name = "allMerchants", query = "SELECT m FROM Merchant m") })
public class Merchant extends BaseObject {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private BigDecimal discount;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
}