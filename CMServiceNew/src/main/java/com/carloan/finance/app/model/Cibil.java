package com.carloan.finance.app.model;

import com.carloan.finance.app.enums.CibilStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Cibil {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cibilId;
	private int cibilScore;
	
	@Enumerated(EnumType.STRING)
	private CibilStatus cibilStatus;
	
	
	
	
}
