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
import com.frantishex.loyaltyprogramme.DTOs.MerchantDTO;
import com.frantishex.loyaltyprogramme.DTOs.MerchantReportDTO;
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

	@Autowired
	ModelMapper modelMapper = new ModelMapper();

	public void createCustomer(Customer customer) {
		customerService.createCustomer(customer);
	}

	public Customer getCustomerById(long id) {
		return customerService.getById(id);
	}

	public List<Customer> getCustomersByMerchant(String name) throws SQLException {

		if (merchantService.getByName(name) != null) {

			return customerService.getCustomersByMerchant(name);
		} else {
			throw new SQLException("No such customer registered with the given merchant name!");
		}
	}

	public List<Customer> getAllCustomers() {
		return customerService.getAll();
	}

	public Customer getCustomerByName(String name) throws SQLException {

		if (customerService.getByName(name) != null) {

			return customerService.getByName(name);
		} else {
			throw new SQLException("No such merchant registered with the given name!");
		}
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

	public Merchant getMerchantByName(String name) throws SQLException {

		if (merchantService.getByName(name) != null) {

			return merchantService.getByName(name);
		} else {
			throw new SQLException("No such merchant registered with the given name!");
		}
	}

	public MerchantReportDTO merchantReport(String name, String dateFrom, String dateTo) throws SQLException, ClassNotFoundException {

		if (merchantService.getByName(name) != null) {

			return merchantService.merchantReport2(name, dateFrom, dateTo);
		} else {
			throw new SQLException("No such merchant registered with the given name!");
		}
	}

	public void createSale(Sale sale) {
		saleService.createSale(sale);
	}

	public Sale getSaleById(long id) {
		return saleService.getById(id);
	}

	public void recalculateClientPoints(Customer client, Sale sale) {
		saleService.recalculateClientPoints(client, sale);
	}

	public List<Sale> getSalesCheaperThan(BigDecimal price) throws SQLException {

		if (saleService.getSalesCheaperThan(price) == null) {
			throw new SQLException("There aren't sales cheaper than the given price!");
		} else {
			return saleService.getSalesCheaperThan(price);
		}
	}

	public List<Sale> getSalesByCustomer(String name) throws SQLException {

		if (customerService.getByName(name) != null) {

			return saleService.getSalesByCustomer(name);
		} else {
			throw new SQLException("No such customer registered with the given name!");
		}
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
			throw new SQLException("No such merchant registered with the given name!");
		}
	}

	public Sale convertToEntity(SalePassingDTO saleCommand) throws SQLException {

		if (customerService.getByName(saleCommand.getCustomer()) != null) {

			Sale sale = modelMapper.map(saleCommand, Sale.class);
			sale.setCustomer(customerService.getByName(saleCommand.getCustomer()));
			sale.setDiscountPercent(customerService.getByName(saleCommand.getCustomer()).getMerchant().getDiscount());
			sale.setDiscountedPrice(saleService.settingDiscountedPrice(sale));
			sale.setDiscountAmount(saleCommand.getPrice().subtract(sale.getDiscountedPrice()));
			sale.setGivenPoints(sale.getDiscountedPrice().multiply(new BigDecimal(5).divide(new BigDecimal(100))));
			return sale;
		} else {

			throw new SQLException("No such customer registered with the given name!");
		}

	}

	public CustomerOutDTO convertToDTO(Customer customer) {

		return modelMapper.map(customer, CustomerOutDTO.class);
	}

	public SaleOutDTO convertToDTO(Sale sale) {

		SaleOutDTO saleDAO = modelMapper.map(sale, SaleOutDTO.class);
		saleDAO.setCustomerName(sale.getCustomer().getName());
		return saleDAO;
	}

	public Merchant convertToEntity(MerchantDTO merchantCommand) {
		Merchant merchant = modelMapper.map(merchantCommand, Merchant.class);
		return merchant;
	}
}
