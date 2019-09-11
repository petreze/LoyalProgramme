package com.frantishex.loyaltyprogramme.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = "merchantByName", query = "SELECT m FROM Merchant m WHERE m.name = :merchantname"),
		@NamedQuery(name = "merchantReport", query = "SELECT m.name, sum(s.discountAmount), avg(s.discountPercent), sum(s.givenPoints)"
				+ " FROM Merchant m, Customer c, Sale s"
				+ " WHERE s.customer = c.id AND c.merchant = m.id AND m.name = :merchantname AND s.date > :dateFrom AND s.date < :dateTo"),
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