package com.carloan.finance.app.exceptions;


@SuppressWarnings("serial")
public class CustomerObjectNotFoundExceptions  extends RuntimeException{

	public CustomerObjectNotFoundExceptions(String message) {
		super(message);
	}
}
