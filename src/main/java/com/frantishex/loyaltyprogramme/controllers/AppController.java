package com.frantishex.loyaltyprogramme.controllers;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.frantishex.loyaltyprogramme.DTOs.CustomerOutDTO;
import com.frantishex.loyaltyprogramme.DTOs.CustomerPassingDTO;
import com.frantishex.loyaltyprogramme.DTOs.MerchantDTO;
import com.frantishex.loyaltyprogramme.DTOs.MerchantReportDTO;
import com.frantishex.loyaltyprogramme.DTOs.SaleOutDTO;
import com.frantishex.loyaltyprogramme.DTOs.SalePassingDTO;
import com.frantishex.loyaltyprogramme.models.Customer;
import com.frantishex.loyaltyprogramme.models.Merchant;
import com.frantishex.loyaltyprogramme.models.Sale;
import com.frantishex.loyaltyprogramme.services.ServiceFacade;

@RestController
public class AppController {

	@Autowired
	private ServiceFacade serviceFacade;

	@PostMapping(value = "/addmerchant")
	@ResponseBody
	public ResponseEntity<String> addMerchant(@Valid @RequestBody MerchantDTO newMerchant) {

		try {

			Merchant merchant = serviceFacade.convertToEntity(newMerchant);
			serviceFacade.createMerchant(merchant);
		} catch (Exception e) {

			e.getMessage();
		}

		return new ResponseEntity<String>("Merchant created!", HttpStatus.OK);
	}

	@GetMapping(value = "/getmerchantbyname")
	@ResponseBody
	public ResponseEntity<Merchant> getMerchantByName(String name) throws SQLException {
		return new ResponseEntity<Merchant>(serviceFacade.getMerchantByName(name), HttpStatus.OK);
	}

	@GetMapping(value = "/getallmerchants")
	@ResponseBody
	public ResponseEntity<List<Merchant>> getAllMerchants() {
		return new ResponseEntity<List<Merchant>>(serviceFacade.getAllMerchants(), HttpStatus.OK);
	}

	@GetMapping(value = "/merchantreport")
	@ResponseBody
	public ResponseEntity<MerchantReportDTO> merchantReport(String merchantName, String dateFrom, String dateTo)
			throws SQLException, ClassNotFoundException {
		return new ResponseEntity<MerchantReportDTO>(serviceFacade.merchantReport(merchantName, dateFrom, dateTo),
				HttpStatus.OK);
	}

	@PostMapping(value = "/addcustomer")
	@ResponseBody
	public ResponseEntity<String> addCustomer(@Valid @RequestBody CustomerPassingDTO newCustomer) throws SQLException {

		try {

			Customer customer = serviceFacade.convertToEntity(newCustomer);
			serviceFacade.createCustomer(customer);
		} catch (Exception e) {

			e.getMessage();
		}

		return new ResponseEntity<String>("Customer created!", HttpStatus.OK);
	}

	@GetMapping(value = "/getcustomerbyname")
	@ResponseBody
	public ResponseEntity<CustomerOutDTO> getCustomerByName(String name) throws SQLException {

		Customer customer = serviceFacade.getCustomerByName(name);
		CustomerOutDTO customerDAO = serviceFacade.convertToDTO(customer);

		return new ResponseEntity<CustomerOutDTO>(customerDAO, HttpStatus.OK);
	}

	@GetMapping(value = "/getallcustomers")
	@ResponseBody
	public ResponseEntity<List<CustomerOutDTO>> getAllCustomers() {

		List<Customer> customers = serviceFacade.getAllCustomers();
		List<CustomerOutDTO> customersOUT = customers.stream().map(customer -> serviceFacade.convertToDTO(customer))
				.collect(Collectors.toList());
		;

		return new ResponseEntity<List<CustomerOutDTO>>(customersOUT, HttpStatus.OK);
	}

	@GetMapping(value = "/getcustomersbymerchant")
	@ResponseBody
	public ResponseEntity<List<CustomerOutDTO>> getCustomersByMerchant(String name) throws SQLException {

		List<Customer> customers = serviceFacade.getCustomersByMerchant(name);
		List<CustomerOutDTO> customersOUT = customers.stream().map(customer -> serviceFacade.convertToDTO(customer))
				.collect(Collectors.toList());

		return new ResponseEntity<List<CustomerOutDTO>>(customersOUT, HttpStatus.OK);
	}

	@PostMapping(value = "/addsale")
	@ResponseBody
	public ResponseEntity<String> addSale(@Valid @RequestBody SalePassingDTO newSale)
			throws ParseException, EntityNotFoundException {

		try {
			Sale sale = new Sale();
			sale = serviceFacade.convertToEntity(newSale);
			serviceFacade.createSale(sale);
			serviceFacade.getCustomerByName(newSale.getCustomer()).stackTurnOver(sale.getDiscountedPrice());
			serviceFacade.createCustomer(serviceFacade.getCustomerByName(newSale.getCustomer()));
			serviceFacade.recalculateClientPoints(serviceFacade.getCustomerByName(newSale.getCustomer()), sale);
		} catch (Exception e) {
			e.getMessage();
		}

		return new ResponseEntity<String>("Sale created!", HttpStatus.OK);
	}

	@GetMapping(value = "/getallsales")
	@ResponseBody
	public ResponseEntity<List<SaleOutDTO>> getAllSales() {

		List<Sale> sales = serviceFacade.getAllSales();
		List<SaleOutDTO> salesOUT = sales.stream().map(sale -> serviceFacade.convertToDTO(sale))
				.collect(Collectors.toList());

		return new ResponseEntity<List<SaleOutDTO>>(salesOUT, HttpStatus.OK);
	}

	@GetMapping(value = "/getsalesunder")
	@ResponseBody
	public ResponseEntity<List<SaleOutDTO>> getSalesUnder(BigDecimal price) throws SQLException {

		List<Sale> sales = serviceFacade.getSalesCheaperThan(price);
		List<SaleOutDTO> salesOUT = sales.stream().map(sale -> serviceFacade.convertToDTO(sale))
				.collect(Collectors.toList());

		return new ResponseEntity<List<SaleOutDTO>>(salesOUT, HttpStatus.OK);
	}

	@GetMapping(value = "/getsalesbycustomer")
	@ResponseBody
	public ResponseEntity<List<SaleOutDTO>> getSalesByCustomer(String name) throws SQLException {

		List<Sale> sales = serviceFacade.getSalesByCustomer(name);
		List<SaleOutDTO> salesOUT = sales.stream().map(sale -> serviceFacade.convertToDTO(sale))
				.collect(Collectors.toList());

		return new ResponseEntity<List<SaleOutDTO>>(salesOUT, HttpStatus.OK);
	}
}