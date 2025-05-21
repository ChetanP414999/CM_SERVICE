package com.carloan.finance.app.serviceImpl;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.carloan.finance.app.repository.SanctionRepository;
import com.carloan.finance.app.service.SanctionService;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

@Service
public class SanctionServiceImpl implements SanctionService {

	
	@Autowired
	private SanctionRepository  sanctionRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private EnquiryRepository enquiryRepository;
	
	
	@Autowired
	JavaMailSender javaMailSender;
	
	
	
	
	
	@Value("${spring.mail.username}") String fromMail;

	private int customerId=0;
	
	
	
	
	
	
	@Override
	public ByteArrayInputStream genrateSanctionletter(  Customer customer) 
	{
		
		//this.customerId=customer.getCustomerId();
		Enquiry enquiry=enquiryRepository.findByCustomerId( customer.getCustomerId());
		//Customer customer=customerRepository.findByCustomerId(customerId);
		
		
		enquiry.setEnquiryStatus("Sanctioned");
		enquiryRepository.save(enquiry);
//		if(enquiry.getCibil().getCibilScore()>500)
//				{
			
				
			double principalAmt = customer.getCustomerTotalLoanRequiredAmmount();
			
		//	System.out.println("-----------------------  "+ principalAmt);
			double annualRate = 8;
			double monthlyRate = annualRate / 12 / 100;
			int tenureMonths =customer.getLoanTentureInMonth();

			//System.out.println("   tenure month   "+tenureMonths);
			double emi = (principalAmt * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths)) /
			             (Math.pow(1 + monthlyRate, tenureMonths) - 1);

			//System.out.println("principle ammount "+principalAmt);
			// Format EMI to 3 decimal places
			DecimalFormat df = new DecimalFormat("#.###");
		double formattedEmi = Double.parseDouble(df.format(emi));

			// Store formatted EMI
		double totalAmountWithInterest= emi * tenureMonths;
		DecimalFormat df1 = new DecimalFormat("#.###");
		double totalAmountWithInterest1 = Double.parseDouble(df.format(totalAmountWithInterest));

	
	//	System.out.println("---------------------        "+formattedEmi);
		
		
		
			SanctionLetter	 letter=new SanctionLetter();
			letter.setSanctionId(customer.getCustomerId());
			letter.setCustomerName(customer.getCustomerName());
			letter.setCustomerMobileNumber(customer.getCustomerMobileNumber());
			letter.setLoanAmtSanctioned(principalAmt);
			letter.setTotalAmountWithInterest(totalAmountWithInterest1);
			letter.setInterestType("simple");
			letter.setRateOfInterest(8);
			
			letter.setSanctionStatus(LoanStatus.Sanctioned);
			
			
	//letter.setSanctionDate(LocalDateTime.now());
	     letter.setLoanTentureInMonth(customer.getLoanTentureInMonth());
	//System.out.println("------------------------------    "+customer.getLoanTentureInMonth());
	       letter.setMonthlyEmiAmount(formattedEmi);
		//	letter.setMonthlyEmiAmount(emi);
			letter.setModeOfPayment("online");
			letter.setRemarks("good");
			letter.setTermsCondition("Repayment from ABC Bank\r\n"
					+ "2. Legal vetting and search to be conducted");
		//
			
			//
		
		Document document = new Document();

		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

	//	PdfWriter.getInstance(document, arrayOutputStream);
		PdfWriter writer = PdfWriter.getInstance(document, arrayOutputStream);
	//	writer.setPageEvent(new WatermarkPageEvent());
		
		document.open();

		Font paragraphFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 25,Font.BOLD, CMYKColor.blue);

		Paragraph paragraph1 = new Paragraph(new Date().toLocaleString());
		paragraph1.setAlignment("right");

		Chunk underlineChunk = new Chunk("BAJAJ FINANCE", paragraphFont);
		underlineChunk.setUnderline(0.1f, -2f);
		underlineChunk	.setBackground(new Color(240, 240, 240));
		Paragraph paragraph = new Paragraph();
		paragraph.add(underlineChunk);
		
		//Paragraph paragraph = new Paragraph("BAJAJ FINANACE", paragraphFont);
		paragraph.setAlignment("center");
		Font bold = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
		Font normal = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
//		Paragraph paragraph2 = new Paragraph(
//				"Thank you for choosing ABC Bank. Based on the application and information provided therein, we are pleased to extend an offer to you for a loan as per the preliminary terms and conditions mentioned below:\r\n");
//		paragraph2.getSpacingBefore();
		Paragraph paragraph2 = new Paragraph();
		paragraph2.add(new Chunk("Thank you for choosing ", normal));
		paragraph2.add(new Chunk("ABC Bank", bold)); // Bold only this
		paragraph2.add(new Chunk(". Based on the application and information provided therein, we are pleased to extend an offer to you for a loan as per the preliminary terms and conditions mentioned below:", normal));

		
		
	//	SanctionLetter letter=createSanctionForCustomer(customer.getCustomerId());
		
		PdfPTable table = new PdfPTable(2);
		table.setSpacingBefore(8f);
		table.setSpacingAfter(8f);
		table.setWidthPercentage(50);

		table.setWidths(new int[] { 6, 7 });
		table.setHorizontalAlignment(Element.ALIGN_LEFT);

		
		
		// cell1.setPadding(5);
				// cell1.setPaddingLeft(40);
		
		PdfPCell cell1 = new PdfPCell();
		cell1.setBackgroundColor(CMYKColor.red);
		cell1.setFixedHeight(30f);
		cell1.setMinimumHeight(20f);
		cell1.setPhrase(new Phrase("Application No"));
		table.addCell(cell1);
		cell1.setPhrase(new Phrase("" + customer.getCustomerId()));
		cell1.setPaddingLeft(40);
		table.addCell(cell1);

		table.completeRow();

		PdfPCell cell2 = new PdfPCell();
//		cell2.setPadding(5);
//		cell2.setPaddingLeft(40);
		cell1.setFixedHeight(30f);
		cell1.setMinimumHeight(20f);
		cell2.setPhrase(new Phrase("Loan Sanction Date"));
		table.addCell(cell2);
		cell2.setPhrase(new Phrase("" + new Date().toLocaleString()));
		table.addCell(cell2);

		PdfPCell cell3 = new PdfPCell();
		cell3.setFixedHeight(30f);
		cell3.setMinimumHeight(20f);
		cell3.setPhrase(new Phrase("Applicant Name"));
		table.addCell(cell3);
		cell3.setPhrase(new Phrase(customer.getCustomerName()));
		table.addCell(cell3);

		PdfPCell cell4 = new PdfPCell();
		cell4.setFixedHeight(30f);
		cell4.setMinimumHeight(20f);
		cell4.setPhrase(new Phrase("Applicant MobileNo"));
		table.addCell(cell4);
		cell4.setPhrase(new Phrase("" + customer.getCustomerMobileNumber()));
		table.addCell(cell4);

		PdfPTable table1 = new PdfPTable(2);
		table1.setSpacingBefore(8f);
		table1.setSpacingAfter(8f);
		table1.setWidthPercentage(100);
		table1.setWidths(new int[] { 4, 9 });
		table1.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell cell5 = new PdfPCell();
		cell5.setBackgroundColor(CMYKColor.red);
		cell5.setFixedHeight(30f);
		cell5.setMinimumHeight(20f);
		cell5.setPhrase(new Phrase("Loan Type"));
		table1.addCell(cell5);
		cell5.setPhrase(new Phrase("Car Loan"));
		table1.addCell(cell5);

		PdfPCell cell6 = new PdfPCell();
		cell6.setFixedHeight(30f);
		cell6.setMinimumHeight(20f);
		cell6.setPhrase(new Phrase("Loan Ammount Sanctioned"));
		table1.addCell(cell6);
		cell6.setPhrase(new Phrase(""+principalAmt));
		table1.addCell(cell6);

		
		PdfPCell cell61 = new PdfPCell();
		cell61.setFixedHeight(30f);
		cell61.setMinimumHeight(20f);
		cell61.setPhrase(new Phrase("Total Amount With Interest"));
		table1.addCell(cell61);
		cell61.setPhrase(new Phrase(""+totalAmountWithInterest1));
		table1.addCell(cell61);

		
		PdfPCell cell7 = new PdfPCell();
		cell7.setFixedHeight(30f);
		cell7.setMinimumHeight(20f);
		cell7.setPhrase(new Phrase("Reference Interest Rate"));
		table1.addCell(cell7);
		cell7.setPhrase(new Phrase(customer.getSanctionLetter().getRateOfInterest()+" %"));
		table1.addCell(cell7);



		PdfPCell cell9 = new PdfPCell();
		cell9.setFixedHeight(30f);
		cell9.setMinimumHeight(20f);
		cell9.setPhrase(new Phrase("Loan Tenure In Month"));
		table1.addCell(cell9);
		cell9.setPhrase(new Phrase(customer.getSanctionLetter().getLoanTentureInMonth()+" Month"));
		table1.addCell(cell9);

		PdfPCell cell11 = new PdfPCell();
		cell11.setFixedHeight(30f);
		cell11.setMinimumHeight(20f);
		cell11.setPhrase(new Phrase("Tota Processing Charge"));
		table1.addCell(cell11);
		cell11.setPhrase(new Phrase("Rs. 2500 "));
		table1.addCell(cell11);



		PdfPCell cell13 = new PdfPCell();
		cell13.setFixedHeight(30f);
		cell13.setMinimumHeight(20f);
		cell13.setPhrase(new Phrase("Sanction Letter Validity"));
		table1.addCell(cell13);
		cell13.setPhrase(new Phrase("15 Days "));
		table1.addCell(cell13);

		PdfPCell cell14 = new PdfPCell();
		cell14.setFixedHeight(30f);
		cell14.setMinimumHeight(20f);
		cell14.setPhrase(new Phrase("Ammount Off EMI (INR)"));
		table1.addCell(cell14);
		cell14.setPhrase(new Phrase("Rs. "+customer.getSanctionLetter().getMonthlyEmiAmount()));
		table1.addCell(cell14);

		PdfPCell cell15 = new PdfPCell();
		cell15.setFixedHeight(30f);
		cell15.setMinimumHeight(20f);
		cell15.setPhrase(new Phrase("Property Address"));
		table1.addCell(cell15);
		cell15.setPhrase(new Phrase(customer.getCustomerAddress().getPermanentAddress().getCityName()+" ,"+customer.getCustomerAddress().getPermanentAddress().getDistrict()+" - "+customer.getCustomerAddress().getPermanentAddress().getPincode()));
		table1.addCell(cell15);

		
		
		
		// Font styles
		Font bold1 = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
	//	Font normal = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

		// Title
		Paragraph title = new Paragraph("Additional conditions to comply prior to loan disbursal:\n\n", bold1);
		

		// Body with sequence numbers
		Paragraph paragraph11 = new Paragraph();
	//	conditions.setFont(normal);

		paragraph11.add("1. Repayment from ABC Bank\n");
		paragraph11.add("2. Legal vetting and search to be conducted\n");
		paragraph11.add("3. NOC and offered collateral\n");
		paragraph11.add("4. Confirmation form, official ID and copy of ID\n");

		

		Paragraph p=new Paragraph("Dear ,"+ customer.getCustomerName());
		
		Image logo=null;
		try {
			//logo = Image.getInstance("https://www.vhv.rs/dpng/d/495-4954654_download-hd-1-bajaj-finserv-logo-png-transparent.png");
			logo=Image.getInstance("https://logowik.com/content/uploads/images/bajaj-finance6835.jpg");
		} catch (BadElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // or use classpath resource
		logo.setAbsolutePosition(30, 770); // Adjust X and Y position
	//	logo.scaleAbsolute(100, 50); // Set width and height
		logo.scaleAbsolute(120, 60);
		document.add(logo);

		

	
		
		document.add(paragraph1);
		paragraph.setSpacingAfter(20);
		document.add(paragraph);
		p.setSpacingAfter(10);
		document.add( p);
		document.add(paragraph2);

		table.setSpacingBefore(20);
		
		document.add(table);
		table.completeRow();

		document.add(table1);
		table1.completeRow();
		document.add(title);
		document.add(paragraph11);
		document.close();

		
		
		
		

		byte[] byteArray = arrayOutputStream.toByteArray();
		
		
	

		ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray);
	
		letter.setSanctionedLetter(byteArray);
		
		//using for looop 
		
		
		
		sanctionRepository.save(letter);
		
		customer.setSanctionLetter(letter);
		customer.setLoanStatus(LoanStatus.Sanctioned);
		customerRepository.save(customer);
		
		
		String text = "Dear "+ customer.getCustomerName() +    "\n\nThank you for choosing ABC Bank. Based on the application and information provided therein, we are pleased to extend an offer to you for a loan as per the preliminary terms and conditions mentioned below:";
      
		DataSource dataSource=new  ByteArrayDataSource(byteArray, "application/pdf");
		
        MimeMessage message=javaMailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper=new MimeMessageHelper(message,true);
		
			helper.setTo("hemantsakarge@gmail.com");
			helper.setFrom(fromMail);
			helper.setSubject("Bajaj Finanace");
			helper.setText(text);
		helper.addAttachment("sanctionletter.pdf ",dataSource);
			//javaMailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
		
		
		return arrayInputStream;
		
	
		
		
}

	
	
	
	
	
	@Override
	public byte[] getDocumentByIdAndType(int docid) {
	
		SanctionLetter sanctionLetter = sanctionRepository.findBysanctionId(docid);
		return sanctionLetter.getSanctionedLetter();
	}


	
//	@Override
//	public ByteArrayInputStream genrateSanctionletter(Customer customer) {
//	    this.customerId = customer.getCustomerId();
//	    Enquiry enquiry = enquiryRepository.findByCustomerId(customer.getCustomerId());
//
//	    if (enquiry.getCibil().getCibilScore() > 650) {
//	        double principalAmt = customer.getCustomerTotalLoanRequiredAmmount();
//	        double annualRate = 8;
//	        double monthlyRate = annualRate / 12 / 100;
//	        int tenureMonths = customer.getLoanTentureInMonth();
//
//	        double emi = (principalAmt * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths)) /
//	                     (Math.pow(1 + monthlyRate, tenureMonths) - 1);
//	        double totalAmountWithInterest = emi * tenureMonths;
//
//	        String formattedEmi = String.format("%.2f", emi);
//	        String formattedTotalWithInterest = String.format("%.2f", totalAmountWithInterest);
//
//	        SanctionLetter letter = new SanctionLetter();
//	        letter.setSanctionId(customer.getCustomerId());
//	        letter.setCustomerName(customer.getCustomerName());
//	        letter.setCustomerMobileNumber(customer.getCustomerMobileNumber());
//	        letter.setLoanAmtSanctioned(principalAmt);
//	        letter.setTotalAmountWithInterest(Double.parseDouble(formattedTotalWithInterest));
//	        letter.setInterestType("simple");
//	        letter.setRateOfInterest(8);
//	        letter.setSanctionStatus(LoanStatus.Sanctioned);
//	        letter.setLoanTentureInMonth(tenureMonths);
//	        letter.setMonthlyEmiAmount(Double.parseDouble(formattedEmi));
//	        letter.setModeOfPayment("online");
//	        letter.setRemarks("good");
//	        letter.setTermsCondition("1. Repayment from ABC Bank\n2. Legal vetting and search to be conducted");
//
//	        Document document = new Document();
//	        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
//
//	        try {
//	            PdfWriter.getInstance(document, arrayOutputStream);
//	            document.open();
//
//	            // Add logo
//	            Image logo = Image.getInstance("https://logowik.com/content/uploads/images/bajaj-finance6835.jpg");
//	            logo.setAbsolutePosition(30, 770);
//	            logo.scaleAbsolute(120, 60);
//	            document.add(logo);
//
//	            // Add header
//	            Font headerFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 25, Font.BOLD, CMYKColor.blue);
//	            Chunk headerChunk = new Chunk("BAJAJ FINANCE", headerFont);
//	            headerChunk.setUnderline(0.1f, -2f);
//	            headerChunk.setBackground(new Color(240, 240, 240));
//	            Paragraph headerParagraph = new Paragraph(headerChunk);
//	            headerParagraph.setAlignment(Element.ALIGN_CENTER);
//	            headerParagraph.setSpacingAfter(20);
//	            document.add(headerParagraph);
//
//	            // Date and greeting
//	            String currentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
//	            document.add(new Paragraph(currentDate));
//	            document.add(new Paragraph("Dear, " + customer.getCustomerName()));
//	            document.add(new Paragraph("Thank you for choosing ABC Bank. Based on the application and information provided therein, we are pleased to extend an offer to you for a loan as per the preliminary terms and conditions mentioned below:"));
//
//	            // Table 1
//	            PdfPTable table = new PdfPTable(2);
//	            table.setSpacingBefore(10);
//	            table.setWidths(new int[]{6, 7});
//	            table.setWidthPercentage(60);
//
//	            table.addCell(createCell("Application No", CMYKColor.red));
//	            table.addCell(createCell(String.valueOf(customer.getCustomerId())));
//
//	            table.addCell(createCell("Loan Sanction Date", CMYKColor.red));
//	            table.addCell(createCell(currentDate));
//
//	            table.addCell(createCell("Applicant Name", CMYKColor.red));
//	            table.addCell(createCell(customer.getCustomerName()));
//
//	            table.addCell(createCell("Applicant MobileNo", CMYKColor.red));
//	            table.addCell(createCell(String.valueOf(customer.getCustomerMobileNumber())));
//
//	            document.add(table);
//
//	            // Table 2
//	            PdfPTable table1 = new PdfPTable(2);
//	            table1.setSpacingBefore(10);
//	            table1.setWidths(new int[]{4, 9});
//	            table1.setWidthPercentage(100);
//
//	            table1.addCell(createCell("Loan Type", CMYKColor.red));
//	            table1.addCell(createCell("Car Loan"));
//
//	            table1.addCell(createCell("Loan Amount Sanctioned", CMYKColor.red));
//	            table1.addCell(createCell(String.valueOf(principalAmt)));
//
//	            table1.addCell(createCell("Total Amount With Interest", CMYKColor.red));
//	            table1.addCell(createCell(formattedTotalWithInterest));
//
//	            table1.addCell(createCell("Reference Interest Rate", CMYKColor.red));
//	            table1.addCell(createCell(annualRate + " %"));
//
//	            table1.addCell(createCell("Loan Tenure In Month", CMYKColor.red));
//	            table1.addCell(createCell(tenureMonths + " Month"));
//
//	            table1.addCell(createCell("Total Processing Charge", CMYKColor.red));
//	            table1.addCell(createCell("Rs. 2500"));
//
//	            table1.addCell(createCell("Sanction Letter Validity", CMYKColor.red));
//	            table1.addCell(createCell("15 Days"));
//
//	            table1.addCell(createCell("EMI Amount (INR)", CMYKColor.red));
//	            table1.addCell(createCell("Rs. " + formattedEmi));
//
//	            table1.addCell(createCell("Property Address", CMYKColor.red));
//	            table1.addCell(createCell(customer.getCustomerAddress().getPermanentAddress().getCityName() + ", " +
//	                    customer.getCustomerAddress().getPermanentAddress().getDistrict() + " - " +
//	                    customer.getCustomerAddress().getPermanentAddress().getPincode()));
//
//	            document.add(table1);
//
//	            // Terms and conditions
//	            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
//	            Paragraph title = new Paragraph("Additional conditions to comply prior to loan disbursal:\n\n", boldFont);
//	            document.add(title);
//
//	            Paragraph conditions = new Paragraph();
//	            conditions.add("1. Repayment from ABC Bank\n");
//	            conditions.add("2. Legal vetting and search to be conducted\n");
//	            conditions.add("3. NOC and offered collateral\n");
//	            conditions.add("4. Confirmation form, official ID and copy of ID\n");
//
//	            document.add(conditions);
//	            document.close();
//
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//
//	        byte[] byteArray = arrayOutputStream.toByteArray();
//	        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray);
//	        letter.setSanctionedLetter(byteArray);
//
//	        sanctionRepository.save(letter);
//
//	        customer.setSanctionLetter(letter);
//	        customer.setLoanStatus(LoanStatus.Pending);
//	        customerRepository.save(customer);
//
//	        // Send email with PDF attached
//	        try {
//	            String text = "Dear " + customer.getCustomerName() + "\n\nThank you for choosing ABC Bank. Based on the application and information provided therein, we are pleased to extend an offer to you for a loan as per the preliminary terms and conditions mentioned below:";
//	            DataSource dataSource = new ByteArrayDataSource(byteArray, "application/pdf");
//
//	            MimeMessage message = javaMailSender.createMimeMessage();
//	            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//	            helper.setTo("hemantsakarge@gmail.com");
//	            helper.setFrom(fromMail);
//	            helper.setSubject("Bajaj Finance");
//	            helper.setText(text);
//	            helper.addAttachment("sanctionletter.pdf", dataSource);
//
//	            // Uncomment below line to enable email in production
//	            // javaMailSender.send(message);
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//
//	        // ✅ Simulate 5-second execution time
//	        try {
//	            Thread.sleep(5000);
//	        } catch (InterruptedException e) {
//	            Thread.currentThread().interrupt();
//	            e.printStackTrace();
//	        }
//
//	        return arrayInputStream;
//	    }
//
//	    return null;
//	}
//
//	// 🔧 Cell Utility Method
//	private PdfPCell createCell(String value, BaseColor bgColor) {
//	    PdfPCell cell = new PdfPCell(new Phrase(value));
//	    cell.setBackgroundColor(bgColor);
//	    cell.setFixedHeight(30f);
//	    cell.setMinimumHeight(20f);
//	    return cell;
//	}
//
//	
//	

	
	
	
	
	
	
	
	
	
	

	
	

}
