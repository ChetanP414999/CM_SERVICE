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
@AllArgsConstructor
@NoArgsConstructor
public class DependantInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int dependantInfoId;
	private int numberOfFamilyMember;
	private String maritalStatus;
	private double familyIncome;
	
}
