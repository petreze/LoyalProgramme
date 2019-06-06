package com.frantishex.loyaltyprogramme.models;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = "customerByName", query = "SELECT c FROM Customer c WHERE c.name = :customername"),

		@NamedQuery(name = "allCustomers", query = "SELECT c FROM Customer c"),

		@NamedQuery(name = "customersByMerchant", query = "SELECT c FROM Customer c, Merchant m "
				+ "WHERE c.merchant = m.id " + "AND m.name = :merchantname") })
public class Customer extends BaseObject {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private BigDecimal turnOver;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "merchant_id", nullable = false)
	private Merchant merchant;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getTurnOver() {
		return turnOver;
	}

	public void setTurnOver(BigDecimal turnOver) {
		this.turnOver = turnOver;
	}

	public void stackTurnOver(BigDecimal amount) {
		this.turnOver = this.turnOver.add(amount);
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
}