package com.frantishex.loyaltyprogramme.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@NamedQueries({ @NamedQuery(name = "salesUnder", query = "SELECT s FROM Sale s WHERE s.price < :price"),

		@NamedQuery(name = "salesByCustomer", query = "SELECT s FROM Sale s, Customer c " + "WHERE s.customer = c.id "
				+ "AND c.name = :customername"),

		@NamedQuery(name = "allSales", query = "SELECT s FROM Sale s") })
public class Sale extends BaseObject {

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@Column(nullable = false, columnDefinition = "DECIMAL(18,2)")
	private BigDecimal price;

	@Column(nullable = false, columnDefinition = "DECIMAL(18,2)")
	private BigDecimal discountPercent;

	@Column(nullable = false, columnDefinition = "DECIMAL(18,2)")
	private BigDecimal discountAmount;

	@Column(nullable = false, columnDefinition = "DECIMAL(18,2)")
	private BigDecimal discountedPrice;

	@Column(nullable = false, columnDefinition = "DECIMAL(18,2)")
	private BigDecimal givenPoints;

	@Column(nullable = false, columnDefinition = "DECIMAL(18,2)")
	private BigDecimal usedPoints;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	@Column(nullable = false)
	private LocalDateTime date = LocalDateTime.now();

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getGivenPoints() {
		return givenPoints;
	}

	public void setGivenPoints(BigDecimal givenPoints) {
		this.givenPoints = givenPoints;
	}

	public BigDecimal getUsedPoints() {
		return usedPoints;
	}

	public void setUsedPoints(BigDecimal usedPoints) {
		this.usedPoints = usedPoints;
	}

	public BigDecimal getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(BigDecimal discount) {
		this.discountPercent = discount;
	}

	public BigDecimal getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(BigDecimal discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}