package com.carloan.finance.app.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.carloan.finance.app.enums.LoanStatus;
import com.carloan.finance.app.model.Customer;
import com.carloan.finance.app.service.CustomerService;
import com.carloan.finance.app.service.SanctionService;

@CrossOrigin("*")
@RestController
public class SanctionController {

	@Autowired
	private SanctionService sanctionService;
	
	@Autowired
	private CustomerService customerService;
	
	
	@Autowired
	RestTemplate rt;
	
	
	

	@GetMapping("/pdf/{customerId}")
	public ResponseEntity<InputStreamResource> genratePdf(@PathVariable("customerId")int customerId)
	{
		
//		System.out.println("-----------------------------");
//		Customer customer=customerService.findByCustomerId(customerId);
//		System.out.println(customer.getCustomerEmail());
//		System.out.println("----------------  " + customerId);
		
		String url = "http://localhost:7073/getBycustomerId/" + customerId;
		
		
		
		
		
		System.out.println("in controller");
		
		
		Customer customer=rt.getForObject(url, Customer.class);
	
		//Customer customer=customerService.findByCustomerId(customerId);
		System.out.println(customer);
		ByteArrayInputStream sanctionletter = sanctionService.genrateSanctionletter(customer);
		
		
		HttpHeaders headers=new HttpHeaders();
	headers.add("Content-Disposition","inline");//"attachment; filename=sanction_letter.pdf"
		//headers.add("Content-Disposition","attachment; filename=sanction_letter.pdf");
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).headers(headers).body(new InputStreamResource(sanctionletter)
				);
		
		
		
		
		
		
		
		
	}
	
	
	@GetMapping("/getDoc/{docid}")
	public ResponseEntity<byte[]> getAllDocumentByIdAndTypa(@PathVariable("docid") int docid) {
		byte[] bb = sanctionService.getDocumentByIdAndType(docid);
		 	HttpHeaders headers=new HttpHeaders();
	headers.add("Content-Disposition","inline");//"attachment; filename=sanction_letter.pdf"
		//headers.add("Content-Disposition","attachment; filename=sanction_letter.pdf");
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).headers(headers).body(bb);
				
		
		
	}
	}



/*
 * if (doctype.equalsIgnoreCase("photo")) { doctype =
 * MediaType.IMAGE_JPEG_VALUE; // doctype="image/jpeg"; return
 * ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, doctype).body(bb); }
 * else {
 */
//	doctype="application/pdf";
/*
 * doctype = MediaType.APPLICATION_PDF_VALUE; return
 * ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, doctype).body(bb);
 */
//	@GetMapping("/genrateSanction/{customerId}")
//	public ResponseEntity<T> genrateSanctionLetter(@PathVariable("customerId")int customerId)
//	{
//		SanctionLetter letter=sanctionService.genrateSanctionletter(customerId);
//	}
	
	
	
		

