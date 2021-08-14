package com.microservices.productcatalog.controller.exceptions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends DefaultError {
	
	private List<FieldValidation> fieldValidationList = new ArrayList<>();
	
	public ValidationError(LocalDate currentDate, Integer statusError, String message) {
		super(currentDate, statusError, message);
	}
	
	public List<FieldValidation> getFieldValidationList() {
		return fieldValidationList;
	}

	public void addFieldValidation(String field, String message) {
		this.fieldValidationList.add(new FieldValidation(field, message));
	}
	
}
