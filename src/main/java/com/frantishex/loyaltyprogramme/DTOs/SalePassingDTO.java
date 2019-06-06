package com.frantishex.loyaltyprogramme.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SalePassingDTO {

	private String customer;

	@Positive(message = "The price should be positive number")
	private BigDecimal price;

	@JsonIgnore
	private LocalDateTime date = LocalDateTime.now();

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}