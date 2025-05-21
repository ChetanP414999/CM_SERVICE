package com.carloan.finance.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carloan.finance.app.enums.LoanStatus;
import com.carloan.finance.app.model.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	
	public Customer findByCustomerId(int customerId);
	
	
	public List<Customer> findByloanStatus(LoanStatus sanctioned);
	
}