package com.carloan.finance.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.carloan.finance.app.enums.LoanStatus;
import com.carloan.finance.app.model.Customer;
import com.carloan.finance.app.service.CustomerService;

@CrossOrigin("*")
@RestController
public class CustomerController {

	
	@Autowired
	CustomerService  customerService;
	
	
	@GetMapping("/viewDoc_verified_customer/{docVerified}")
	public ResponseEntity<List<Customer>> viewDocVerifiedCustomer(@PathVariable("docVerified") LoanStatus docVerified)
	{
		
		List<Customer>customers=customerService.viewDocVerifiedCustomer(docVerified);
		
		return new ResponseEntity<List<Customer>>(customers,HttpStatus.OK);
		
	}
	
	@GetMapping("/genrateSanctioned/{customerId}")
	public ResponseEntity<Customer> genrateSanctioned(@PathVariable("customerId")int customerId)
	{
		Customer customer=customerService.genrateSanctioned(customerId);
		System.out.println("---------------------- cm controller--------");
		System.out.println(customer);
		return new ResponseEntity<Customer>(customer,HttpStatus.OK);
		
		
	}
	
	

	@GetMapping("/view_SanctionedCustomer/{sanctioned}")
	public ResponseEntity<List<Customer>> viewSanctionedCustomer(@PathVariable("sanctioned") LoanStatus sanctioned)
	{
		
		List<Customer>customers=customerService.viewSanctionedCustomer(sanctioned);
		
		return new ResponseEntity<List<Customer>>(customers,HttpStatus.OK);
		
	}
		
	
}
