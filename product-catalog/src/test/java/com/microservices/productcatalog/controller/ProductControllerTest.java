package com.microservices.productcatalog.controller;

import static com.microservices.productcatalog.utils.ProductUtils.createFakeProductDTO;
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

import com.microservices.productcatalog.dto.ProductDTO;
import com.microservices.productcatalog.service.ProductService;

import io.restassured.http.ContentType;

@WebMvcTest
public class ProductControllerTest {
	
	@Autowired
	private ProductController productController;
	
	@MockBean
	private ProductService productService;
	
	private final String PATH = "http://localhost:8081/v1/product";
	
	@BeforeEach
	public void setUp() {
		standaloneSetup(this.productController);
	}
	
	@Test
	void create_ReturnsCreated_WhenSuccessful() {
		ProductDTO expectedProductDTO = createFakeProductDTO();
		expectedProductDTO.setId(1L);
		
		when(productService.create(any(ProductDTO.class))).thenReturn(expectedProductDTO);
		
		given().log().all()
			.contentType(ContentType.JSON)
			.body(expectedProductDTO)
		.when()
			.post(PATH)
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	void findById_ReturnsProductDTO_WhenSuccessful() {
		ProductDTO expectedProductDTO = createFakeProductDTO();
		expectedProductDTO.setId(2L);
		when(productService.findById(any(Long.class))).thenReturn(expectedProductDTO);
		
		given().log().all()
			.contentType(ContentType.JSON)
		.when()
			.get(PATH + "/{id}", expectedProductDTO.getId())
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("id", is(2))
			.body("name", is(expectedProductDTO.getName()))
			.body("amount", is(expectedProductDTO.getAmount()));
	}
	
	@Test
	void findAll_ReturnsListOfProductDTO_WhenSuccessful() {
		ProductDTO firstExpectedProductDTO = createFakeProductDTO();
		firstExpectedProductDTO.setId(3L);
		
		ProductDTO secondExpectedProductDTO = createFakeProductDTO();
		secondExpectedProductDTO.setId(4L);
		secondExpectedProductDTO.setName("Second Test Product");
		
		List<ProductDTO>peopleDTO = new ArrayList<>();
		peopleDTO.add(firstExpectedProductDTO);
		peopleDTO.add(secondExpectedProductDTO);
		
		when(productService.findAll()).thenReturn(peopleDTO);
		
		given().log().all()
			.contentType(ContentType.JSON)
		.when()
			.get(PATH)
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("[0].id", is(3))
			.body("[0].name", is(firstExpectedProductDTO.getName()))
			.body("[0].name", is("Test Product"))
			.body("[0].amount", is(firstExpectedProductDTO.getAmount()))
			
			.body("[1].id", is(4))
			.body("[1].name", is(secondExpectedProductDTO.getName()))
			.body("[1].name", is("Second Test Product"))
			.body("[1].amount", is(secondExpectedProductDTO.getAmount()));
	}
	
	@Test
	void update_ReturnsUpdatedProductDTO_WhenSuccessful() {
		ProductDTO productDTOUpdated = createFakeProductDTO();
		productDTOUpdated.setId(5L);
		
		when(productService.update(any(Long.class), any(ProductDTO.class))).thenReturn(productDTOUpdated);
		
		productDTOUpdated.setName("Updated Test Product");
		
		given().log().all()
			.contentType(ContentType.JSON)
			.body(productDTOUpdated)
		.when()
			.put(PATH + "/{id}", 5)
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("id", is(5))
			.body("name", is(productDTOUpdated.getName()))
			.body("name", is("Updated Test Product"))
			.body("amount", is(productDTOUpdated.getAmount()));
	}
	
	@Test
	void delete_ReturnsNoContent_WhenSuccessful() {
		
		doNothing().when(productService).delete(any(Long.class));
		
		given().log().all()
			.contentType(ContentType.JSON)
		.when()
			.delete(PATH + "/{id}", 1)
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}

}
