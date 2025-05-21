package com.carloan.finance.app.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

	private String message;
	private LocalDateTime dateTime;
	private String url;
	private HttpStatus httpStatus;
	
}
