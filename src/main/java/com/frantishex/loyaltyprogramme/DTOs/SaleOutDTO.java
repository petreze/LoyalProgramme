package com.frantishex.loyaltyprogramme.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SaleOutDTO {

	private String customerName;

	private BigDecimal discount;

	private BigDecimal discountedPrice;

	@JsonIgnore
	private LocalDateTime date = LocalDateTime.now();

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
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
}