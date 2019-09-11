package com.frantishex.loyaltyprogramme.DTOs;

import java.math.BigDecimal;

public class CustomerOutDTO {

	private String name;

	private BigDecimal turnOver;

	private BigDecimal points;

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

	public BigDecimal getPoints() {
		return points;
	}

	public void setPoints(BigDecimal points) {
		this.points = points;
	}
}