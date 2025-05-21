package com.carloan.finance.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LoanDisbursement {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int disbursedmentId;
	private int agrrementId;
	private String customerName;
	private long accountNumber;
	private String ifscCode;
	private String bankName;
	private String accountType;
	private int loanNo;
	
	
	private String agreementDate;
	private String amountPayTye;
	private double totalAmount;
	
	
	
	private double transferAmount;
	private String paymentStatus;
	private String amountPaidDate;
	
	
	
	
	
	
	
	
}
