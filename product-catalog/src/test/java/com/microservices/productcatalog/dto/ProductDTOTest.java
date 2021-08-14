package com.microservices.productcatalog.dto;

import static com.microservices.productcatalog.utils.ProductUtils.createFakeProductDTO;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductDTOTest {
	
	private final String NAME_IS_BLANK = "The name field must be filled";
	
	private String AMOUNT_IS_NULL = "The amount field must be filled";
	
	private String AMOUNT_IS_LESS_THAN_MINIMUM = "The amount of products cannot be less than zero";
	
	private Validator validator;
	
	@BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	
	@Test
    void test_NoViolations_WhenSuccessful() {
		ProductDTO productDTO =  createFakeProductDTO();

		Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
		
		assertTrue(violations.isEmpty());
    }
	
	@Test
    void test_ExistsViolation_WhenNameIsBlank() {
		ProductDTO productDTO =  createFakeProductDTO();
		productDTO.setName("   ");

		Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
				
		assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(NAME_IS_BLANK)));
    }
	
	@Test
    void test_ExistsViolation_WhenAmountIsNull() {
		ProductDTO productDTO =  new ProductDTO();
		productDTO.setName("Name Product");
		
		Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
				
		assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(AMOUNT_IS_NULL)));
    }
	
	@Test
    void test_ExistsViolation_WhenAmountIsLess_Than_Minimum() {
		ProductDTO productDTO =  createFakeProductDTO();
		productDTO.setAmount(-1);
		
		Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
				
		assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(AMOUNT_IS_LESS_THAN_MINIMUM)));
    }

}
