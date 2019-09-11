package com.frantishex.loyaltyprogramme.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SaleOutDTO {

	private String customerName;

	private BigDecimal discountPercent;

	private BigDecimal discountAmount;

	private BigDecimal discountedPrice;
	
	private BigDecimal givenPoints;

	private BigDecimal usedPoints;

	@JsonIgnore
	private LocalDateTime date = LocalDateTime.now();

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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
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
}