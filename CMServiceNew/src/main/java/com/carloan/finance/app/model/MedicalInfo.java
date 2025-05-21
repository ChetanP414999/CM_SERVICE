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
public class MedicalInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int billId;
	private int patientId;
	private String professionPatientName;
	private String bilingDate;
	private double loanAmount;
	private String treatement;
}
