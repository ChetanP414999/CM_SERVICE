package com.carloan.finance.app.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Ledger {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ledgerId;
	private String  ledgerCreatedDate;
	 private double totalLoanAmount;
	private double paybleAmountWithInterest;
	private int tenure;
	private int installmentNumber;
	private double monthlyEmi;
	private double nextMonthEmiWithRemainingEmi;
	private double amountPaidPerMonth;
	private double amountPaidTillDate;
	private double remainingAmount;
	private LocalDate nextEmiDateStart;
	private LocalDate nextEmiDateEnd;
	
	private String previousEmiStatus;
	private String currentMonthEmiStatus;
	private LocalDate loanEndDate;
	private String loanStatus;
	
	
	
	
	
}
