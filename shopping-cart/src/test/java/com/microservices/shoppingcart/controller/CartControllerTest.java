package com.microservices.shoppingcart.controller;

import static com.microservices.shoppingcart.utils.CartUtils.createFakeCartDTO;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.microservices.shoppingcart.dto.CartDTO;
import com.microservices.shoppingcart.service.CartService;

import io.restassured.http.ContentType;

@WebMvcTest
public class CartControllerTest {
	
	@Autowired
	private CartController cartController;
	
	@MockBean
	private CartService cartService;
	
	private final String PATH = "http://localhost:8082/v1/cart";
	
	@BeforeEach
	public void setUp() {
		standaloneSetup(this.cartController);
	}
	
	@Test
	void create_ReturnsCreated_WhenSuccessful() {
		CartDTO expectedCartDTO = createFakeCartDTO();
		expectedCartDTO.setId(1L);
		
		when(cartService.create(any(CartDTO.class))).thenReturn(expectedCartDTO);
		
		given().log().all()
			.contentType(ContentType.JSON)
			.body(expectedCartDTO)
		.when()
			.post(PATH)
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	void findById_ReturnsCartDTO_WhenSuccessful() {
		CartDTO expectedCartDTO = createFakeCartDTO();
		expectedCartDTO.setId(2L);
		when(cartService.findById(any(Long.class))).thenReturn(expectedCartDTO);
		
		given().log().all()
			.contentType(ContentType.JSON)
		.when()
			.get(PATH + "/{id}", expectedCartDTO.getId())
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("id", is(2))
			.body("items[0].productId", is(expectedCartDTO.getItems().get(0).getProductId().intValue()))
			.body("items[0].amount", is(expectedCartDTO.getItems().get(0).getAmount()))
			.body("items[0].product.name", is(expectedCartDTO.getItems().get(0).getProduct().getName()));
	}
	
	@Test
	void findAll_ReturnsListOfProductDTO_WhenSuccessful() {
		CartDTO firstExpectedCartDTO = createFakeCartDTO();
		firstExpectedCartDTO.setId(3L);
		
		CartDTO secondExpectedCartDTO = createFakeCartDTO();
		secondExpectedCartDTO.setId(4L);
		secondExpectedCartDTO.getItems().get(0).setProductId(2L);
		secondExpectedCartDTO.getItems().get(0).setAmount(20);
		
		List<CartDTO>cartsDTO = new ArrayList<>();
		cartsDTO.add(firstExpectedCartDTO);
		cartsDTO.add(secondExpectedCartDTO);
		
		when(cartService.findAll()).thenReturn(cartsDTO);
		
		given().log().all()
			.contentType(ContentType.JSON)
		.when()
			.get(PATH)
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("[0].id", is(3))
			.body("[0].items[0].productId", is(firstExpectedCartDTO.getItems().get(0).getProductId().intValue()))
			.body("[0].items[0].amount", is(firstExpectedCartDTO.getItems().get(0).getAmount()))
			.body("[0].items[0].product.name", is(firstExpectedCartDTO.getItems().get(0).getProduct().getName()))
			
			.body("[1].id", is(4))
			.body("[1].items[0].productId", is(secondExpectedCartDTO.getItems().get(0).getProductId().intValue()))
			.body("[1].items[0].amount", is(secondExpectedCartDTO.getItems().get(0).getAmount()))
			.body("[1].items[0].product.name", is(secondExpectedCartDTO.getItems().get(0).getProduct().getName()));
	}
	
	@Test
	void delete_ReturnsNoContent_WhenSuccessful() {
		
		doNothing().when(cartService).delete(any(Long.class));
		
		given().log().all()
			.contentType(ContentType.JSON)
		.when()
			.delete(PATH + "/{id}", 1)
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}

}
