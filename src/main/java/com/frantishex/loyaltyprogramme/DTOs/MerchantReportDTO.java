package com.frantishex.loyaltyprogramme.DTOs;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MerchantReportDTO {
	
	private String name;

	private BigDecimal discountAmount;
	
	private BigDecimal discountPercent;

	private BigDecimal givenPoints;

	public BigDecimal getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(BigDecimal discountPercent) {
		this.discountPercent = discountPercent;
	}

	public BigDecimal getGivenPoints() {
		return givenPoints;
	}

	public void setGivenPoints(BigDecimal givenPoints) {
		this.givenPoints = givenPoints;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}