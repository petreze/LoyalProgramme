package com.frantishex.loyaltyprogramme.services;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frantishex.loyaltyprogramme.models.Customer;

@Service
@Transactional
public class CustomerService {

	@Autowired
	EntityManager em;

	public void createCustomer(Customer customer) {
		em.merge(customer);
	}

	public Customer getById(long id) {
		return em.find(Customer.class, id);
	}

	public List<Customer> getAll() {
		return em.createNamedQuery("allCustomers", Customer.class).setMaxResults(100).getResultList();
	}

	public Customer getByName(String name) {
		return em.createNamedQuery("customerByName", Customer.class).setParameter("customername", name).getResultList()
				.stream().findFirst().orElse(null);
	}

	public List<Customer> getCustomersByMerchant(String name) {

		return em.createNamedQuery("customersByMerchant", Customer.class).setParameter("merchantname", name)
				.setMaxResults(100).getResultList();
	}
}