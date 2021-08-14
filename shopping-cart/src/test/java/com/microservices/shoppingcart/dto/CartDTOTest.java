package com.microservices.shoppingcart.dto;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microservices.shoppingcart.utils.CartUtils.createFakeCartDTO;

public class CartDTOTest {
	
	private static final String LIST_ITEMS_IS_NULL = "The items must be informated";
	
	private Validator validator;
	
	@BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	
	@Test
    void test_NoViolations_WhenSuccessful() {
		CartDTO cartDTO =  createFakeCartDTO();

		Set<ConstraintViolation<CartDTO>> violations = validator.validate(cartDTO);
		
		assertTrue(violations.isEmpty());
    }
	
	@Test
    void test_ExistsViolation_WhenListItemsIsNull() {
		CartDTO cartDTO = new CartDTO();
		
		Set<ConstraintViolation<CartDTO>> violations = validator.validate(cartDTO);
				
		assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals(LIST_ITEMS_IS_NULL)));
    }

}
