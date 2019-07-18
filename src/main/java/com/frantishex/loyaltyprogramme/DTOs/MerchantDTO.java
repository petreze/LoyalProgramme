package com.frantishex.loyaltyprogramme.DTOs;

import java.math.BigDecimal;

public class MerchantDTO {

	private String name;

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
