package com.carloan.finance.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllPersonalDocuments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int documentId;
	
	@Lob
	@Column(length = 999999999)
	private byte[] addressProof;
	@Lob
	@Column(length = 999999999)
	private byte[] panCard;
	@Lob
	@Column(length = 999999999)
	private byte[] incomeTax;
	@Lob
	@Column(length = 999999999)
	private byte[] adharCard;
	@Lob
	@Column(length = 999999999)
	private byte[] photo;
	@Lob
	@Column(length = 999999999)
	private byte[] signature;
	@Lob
	@Column(length = 999999999)
	private byte[] bankCheque;
	@Lob
	@Column(length = 999999999)
	private byte[] salarySlip;
	
	
}
