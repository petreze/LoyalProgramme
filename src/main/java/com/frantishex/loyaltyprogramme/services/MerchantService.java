package com.frantishex.loyaltyprogramme.services;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frantishex.loyaltyprogramme.models.Merchant;

@Service
@Transactional
public class MerchantService {

	@Autowired
	private EntityManager em;

	void createMerchant(Merchant merchant) {
		em.merge(merchant);
	}

	Merchant getById(long id) {
		return em.find(Merchant.class, id);
	}

	public List<Merchant> getAll() {
		return em.createNamedQuery("allMerchants", Merchant.class).setMaxResults(100).getResultList();
	}

	public Merchant getByName(String name) {
		return em.createNamedQuery("MerchantByName", Merchant.class).setParameter("merchantname", name).getResultList()
				.stream().findFirst().orElse(null);
	}
}