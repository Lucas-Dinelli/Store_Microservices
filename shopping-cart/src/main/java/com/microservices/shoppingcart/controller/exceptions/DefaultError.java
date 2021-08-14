package com.microservices.shoppingcart.controller.exceptions;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefaultError {
	
	private LocalDate currentDate;
	private Integer statusError;
	private String message;

}
