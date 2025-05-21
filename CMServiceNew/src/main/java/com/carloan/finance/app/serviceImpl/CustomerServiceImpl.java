package com.carloan.finance.app.serviceImpl;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.carloan.finance.app.enums.LoanStatus;
import com.carloan.finance.app.model.Customer;
import com.carloan.finance.app.model.Enquiry;
import com.carloan.finance.app.model.SanctionLetter;
import com.carloan.finance.app.repository.CustomerRepository;
import com.carloan.finance.app.repository.EnquiryRepository;
import com.carloan.finance.app.service.CustomerService;

import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

@Service
public class CustomerServiceImpl implements CustomerService {

	
	
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	EnquiryRepository enquiryRepository;
	
	@Autowired
	JavaMailSender javaMailSender;
	
	
	@Value("${spring.mail.username}") String fromMail;

	
	
	
	@Override
	public Customer findByCustomerId(int customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer genrateSanctioned(int customerId) {
		
		
		Enquiry enquiry=enquiryRepository.findByCustomerId( customerId);
		Customer customer=customerRepository.findByCustomerId(customerId);
		enquiry.setEnquiryStatus("Sanctioned");
		enquiryRepository.save(enquiry);
		if(enquiry.getCibil().getCibilScore()>500)
		{
			double principalAmt = customer.getCustomerTotalLoanRequiredAmmount();
			
			
				double annualRate = 8;
				double monthlyRate = annualRate / 12 / 100;
				int tenureMonths =customer.getLoanTentureInMonth();
				
				double emi = (principalAmt * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths)) /
			             (Math.pow(1 + monthlyRate, tenureMonths) - 1);
						
				// Format EMI to 3 decimal places
				DecimalFormat df = new DecimalFormat("#.###");
			double formattedEmi = Double.parseDouble(df.format(emi));

				// Store formatted EMI
			double totalAmountWithInterest= emi * tenureMonths;
			DecimalFormat df1 = new DecimalFormat("#.###");
			double totalAmountWithInterest1 = Double.parseDouble(df.format(totalAmountWithInterest));

			
			SanctionLetter	 letter=new SanctionLetter();
			letter.setSanctionId(customer.getCustomerId());
			letter.setCustomerName(customer.getCustomerName());
			letter.setCustomerMobileNumber(customer.getCustomerMobileNumber());
			letter.setLoanAmtSanctioned(principalAmt);
			letter.setTotalAmountWithInterest(totalAmountWithInterest1);
			letter.setInterestType("simple");
			letter.setRateOfInterest(8);
			
			letter.setSanctionStatus(LoanStatus.Sanctioned);
			letter.setLoanTentureInMonth(customer.getLoanTentureInMonth());
			  
			  letter.setMonthlyEmiAmount(formattedEmi);
			
					letter.setModeOfPayment("online");
					letter.setRemarks("good");
					letter.setTermsCondition("Repayment from ABC Bank\r\n"
							+ "2. Legal vetting and search to be conducted");
				
					
					//sanctionRepository.save(letter);
					
					customer.setSanctionLetter(letter);
					customer.setLoanStatus(LoanStatus.Sanctioned);
					customerRepository.save(customer);
					
					String text = "Dear "+ customer.getCustomerName() +    "\n\nThank you for choosing ABC Bank. Based on the application and information provided therein, we are pleased to extend an offer to you for a loan as per the preliminary terms and conditions mentioned below:";
				      
					//DataSource dataSource=new  ByteArrayDataSource(byteArray, "application/pdf");
					
			        MimeMessage message=javaMailSender.createMimeMessage();
					
					try {
						MimeMessageHelper helper=new MimeMessageHelper(message,true);
					
						helper.setTo("hemantsakarge@gmail.com");
						helper.setFrom(fromMail);
						helper.setSubject("Bajaj Finanace");
						helper.setText(text);
					//helper.addAttachment("sanctionletter.pdf ",dataSource);
						//javaMailSender.send(message);
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			
			return customer;
		}
		return null;
		
		
		
		
		
	}


	@Override
	public List<Customer> viewDocVerifiedCustomer(LoanStatus docVerified) {
		
		
		List<Customer>customers=customerRepository.findByloanStatus(docVerified);
		
		
		return customers;
	}


	@Override
	public List<Customer> viewSanctionedCustomer(LoanStatus sanctioned) {
		
		
		List<Customer>customers=customerRepository.findByloanStatus(sanctioned);
		
		
		
		return customers;
	}


	
	

	

}
