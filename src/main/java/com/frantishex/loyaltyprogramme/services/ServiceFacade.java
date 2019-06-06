package com.frantishex.loyaltyprogramme.services;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frantishex.loyaltyprogramme.DTOs.CustomerOutDTO;
import com.frantishex.loyaltyprogramme.DTOs.CustomerPassingDTO;
import com.frantishex.loyaltyprogramme.DTOs.SaleOutDTO;
import com.frantishex.loyaltyprogramme.DTOs.SalePassingDTO;
import com.frantishex.loyaltyprogramme.models.Customer;
import com.frantishex.loyaltyprogramme.models.Merchant;
import com.frantishex.loyaltyprogramme.models.Sale;

@Service
@Transactional
public class ServiceFacade {

	@Autowired
	CustomerService customerService;

	@Autowired
	SaleService saleService;

	@Autowired
	MerchantService merchantService;

	ModelMapper modelMapper = new ModelMapper();

	public void createCustomer(Customer customer) {
		customerService.createCustomer(customer);
	}

	public Customer getCustomerById(long id) {
		return customerService.getById(id);
	}

	public List<Customer> getCustomersByMerchant(String name) {
		return customerService.getCustomersByMerchant(name);
	}

	public List<Customer> getAllCustomers() {
		return customerService.getAll();
	}

	public Customer getCustomerByName(String name) {
		return customerService.getByName(name);
	}

	public void createMerchant(Merchant merchant) {
		merchantService.createMerchant(merchant);
	}

	public Merchant getMerchantById(long id) {
		return merchantService.getById(id);
	}

	public List<Merchant> getAllMerchants() {
		return merchantService.getAll();
	}

	public Merchant getMerchantByName(String name) {
		return merchantService.getByName(name);
	}

	public void createSale(Sale sale) {
		saleService.createSale(sale);
	}

	public Sale getSaleById(long id) {
		return saleService.getById(id);
	}

	public List<Sale> getSalesCheaperThan(BigDecimal price) {
		return saleService.getCheaperThan(price);
	}

	public List<Sale> getSalesByCustomer(String name) {
		return saleService.getSalesByCustomer(name);
	}

	public List<Sale> getAllSales() {
		return saleService.getAll();
	}

	public Customer convertToEntity(CustomerPassingDTO customerCommand) throws SQLException {

		if (merchantService.getByName(customerCommand.getMerchant()) != null) {

			Customer customer = modelMapper.map(customerCommand, Customer.class);

			customer.setTurnOver(new BigDecimal(0));
			customer.setMerchant(merchantService.getByName((customerCommand.getMerchant())));

			return customer;
		} else {
			throw new SQLException("No such merchant registered with that name!");
		}
	}

	public Sale convertToEntity(SalePassingDTO saleCommand) throws SQLException {

		if (customerService.getByName(saleCommand.getCustomer()) != null) {

			Sale sale = modelMapper.map(saleCommand, Sale.class);
			sale.setCustomer(customerService.getByName(saleCommand.getCustomer()));
			sale.setDiscount(customerService.getByName(saleCommand.getCustomer()).getMerchant().getDiscount());
			sale.setDiscountedPrice(saleService.settingDiscountedPrice(sale));

			return sale;
		} else {

			throw new SQLException("No such customer registered with that name!");
		}

	}

	public CustomerOutDTO convertToDAO(Customer customer) {

		return modelMapper.map(customer, CustomerOutDTO.class);
	}

	public SaleOutDTO convertToDAO(Sale sale) {

		SaleOutDTO saleDAO = modelMapper.map(sale, SaleOutDTO.class);
		saleDAO.setCustomerName(sale.getCustomer().getName());
		return saleDAO;
	}
}
