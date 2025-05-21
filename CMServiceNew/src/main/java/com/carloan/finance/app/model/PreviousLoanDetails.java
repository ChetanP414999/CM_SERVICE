package com.carloan.finance.app.model;

import com.carloan.finance.app.enums.PreviousLoanStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreviousLoanDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int previousLoanId;
	private double previousLoanAmount;
	private int previousLoanTenure;
	private double previousLoanPaid;
	private double previousLoanRemeaningAmount;
	private int previousLoanDefaulterCount;
	
	@Enumerated(EnumType.STRING)
	private PreviousLoanStatus previousLoanStatus;
	private String previousLoanRemark;
	@OneToOne(cascade = CascadeType.ALL)
	private PreviousLoanBank previousLoanBank;
}
