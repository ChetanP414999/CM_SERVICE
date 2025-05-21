package com.carloan.finance.app.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	@Id
	private int customerId;
	private String customerName;
	private String customerDateOfBirth;
	private int customerAge;
	
	private String gender;
	
	private String pancardNo;
	private String adharCard;
	
	private String customerEmail;
	private long customerMobileNumber;
	private long customerAdditionalMobileNumber;
	private double customerAmmountPaidForCarloan;
	private double customerTotalLoanRequiredAmmount;
	
	private int loanTentureInMonth;
	
	
	private String userName;
	private String password;
	

	@Enumerated(EnumType.STRING)
	private com.carloan.finance.app.enums.LoanStatus loanStatus;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	private AllPersonalDocuments allPersonalDocuments;
	
	@OneToOne(cascade = CascadeType.ALL)
	private DependantInformation dependantInformation;
	
	@OneToOne(cascade = CascadeType.ALL)
	private CustomerAddress customerAddress;
	
	@OneToOne(cascade = CascadeType.ALL)
	private MedicalInfo medicalInfo;

	@OneToOne(cascade = CascadeType.ALL)
	private PreviousLoanDetails previousLoanDetails;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	private GuarantorDetails guarantorDetails;
	
@OneToOne(cascade = CascadeType.ALL)
	private SanctionLetter sanctionLetter;


@OneToOne(cascade = CascadeType.ALL)
private LoanDisbursement disbursement;


@OneToOne(cascade = CascadeType.ALL)
private AccountDetails accountDetails;


@OneToMany(cascade = CascadeType.ALL)
private List<Ledger> ledger=new ArrayList<Ledger>();
}













