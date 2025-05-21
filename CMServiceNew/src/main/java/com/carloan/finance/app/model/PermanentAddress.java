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
public class PermanentAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int permanentAddressId;
	private String areaName;
	private String cityName;
	private String district;
	private String state;
	private long pincode;
	private int houseNumber;
	private String streetName;
	
	
	
}
