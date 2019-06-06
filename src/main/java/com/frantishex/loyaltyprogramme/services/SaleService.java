package com.frantishex.loyaltyprogramme.services;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frantishex.loyaltyprogramme.models.Sale;

@Service
@Transactional
public class SaleService {

	@Autowired
	EntityManager em;

	public void createSale(Sale sale) {
		em.merge(sale);
	}

	public Sale getById(long id) {
		return em.find(Sale.class, id);

	}

	public List<Sale> getAll() {
		return em.createNamedQuery("allSales", Sale.class).setMaxResults(100).getResultList();
	}

	public List<Sale> getCheaperThan(BigDecimal price) {

		return em.createNamedQuery("salesUnder", Sale.class).setParameter("price", price).setMaxResults(100)
				.getResultList();
	}

	public List<Sale> getSalesByCustomer(String name) {

		return em.createNamedQuery("salesByCustomer", Sale.class).setParameter("customername", name).setMaxResults(100)
				.getResultList();
	}

	public BigDecimal settingDiscountedPrice(Sale sale) {

		BigDecimal discount = sale.getDiscount();

		BigDecimal discountedPrice = sale.getPrice()
				.subtract(sale.getPrice().multiply(discount).divide(new BigDecimal(100)));

		return discountedPrice;
	}
}