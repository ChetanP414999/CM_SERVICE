package com.carloan.finance.app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enquiry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerId;
	private String   customerName;;
	
	private int customerAge;
	private String customerEmail;
	private long customerMobileNumber;
	private String pancardNo;
	private String adharCard;
	private String gender;
	
	
	private String userName;
	private String password;
	
	
	private String enquiryStatus;
	@OneToOne(cascade = CascadeType.ALL)
	private Cibil cibil;
}
