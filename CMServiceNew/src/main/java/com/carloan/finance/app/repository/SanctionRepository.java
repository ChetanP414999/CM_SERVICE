package com.carloan.finance.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carloan.finance.app.model.Customer;
import com.carloan.finance.app.model.SanctionLetter;

@Repository
public interface SanctionRepository  extends JpaRepository<SanctionLetter, Integer>{

//	public Customer findByCustomerId(int customerId);
	public SanctionLetter findBysanctionId(int sanctionId);
	
	
}
