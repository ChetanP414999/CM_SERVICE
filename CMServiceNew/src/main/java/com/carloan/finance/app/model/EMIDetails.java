package com.carloan.finance.app.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class EMIDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int emiId;
	private double emiAmountMonthly;
	private LocalDateTime nextEmiDueDate;
	private String previousEmiStatus;
	
	
}
