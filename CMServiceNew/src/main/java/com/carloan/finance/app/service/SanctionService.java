package com.carloan.finance.app.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.carloan.finance.app.enums.LoanStatus;
import com.carloan.finance.app.model.Customer;
import com.carloan.finance.app.model.Enquiry;
import com.carloan.finance.app.model.SanctionLetter;

public interface SanctionService {

//public 	ByteArrayInputStream genrateSanctionletter(Customer customer);


//public SanctionLetter createSanctionForCustomer(int customerId);

//public Enquiry findByCustomerId(int customerId);


//public byte[] getDocumentFile(int id);


public byte[] getDocumentByIdAndType(int docid);

public ByteArrayInputStream genrateSanctionletter(Customer  customer);




}
