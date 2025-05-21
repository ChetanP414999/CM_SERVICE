package com.carloan.finance.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetails {
    
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountId;
	private String accountType;
	private long accountBalance;
	private String accountHolderName;
	private String accountStatus;
	private long accountNumber;
	
	
	
}
