package com.carloan.finance.app.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.carloan.finance.app.enums.LoanStatus;
import com.carloan.finance.app.model.Customer;
import com.carloan.finance.app.model.Enquiry;
import com.carloan.finance.app.model.LocalAddress;

public interface CustomerService {
	
	
	public Customer findByCustomerId(int customerId);

	public List<Customer> viewDocVerifiedCustomer(LoanStatus docVerified);

	public List<Customer> viewSanctionedCustomer(LoanStatus sanctioned);

	public Customer genrateSanctioned(int customerId);
	
}
