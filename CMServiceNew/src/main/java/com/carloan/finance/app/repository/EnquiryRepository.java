package com.carloan.finance.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carloan.finance.app.model.Enquiry;
@Repository
public interface EnquiryRepository  extends JpaRepository<Enquiry, Integer>{

	
	public Enquiry findByCustomerId(int customerId);
	
	
	
}
