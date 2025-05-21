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
public class GuarantorDetails {
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int guarantorId;
private String guarantorName;
private String guarantorDateOfBirth;
private String guarantorRelationshipwithCustomer;
private long guarantorMobileNumber;
private long guarantorAdharCardNo;

private String guarantorMortgageDetails;
private String guarantorjobDetails;
private String guarantorLocalAddress;
private String guarantorPermanentAddress;	


}
