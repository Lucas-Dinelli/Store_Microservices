package com.microservices.productcatalog.repository;

import static com.microservices.productcatalog.utils.ProductUtils.createFakeProductModel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.microservices.productcatalog.model.Product;

@DataJpaTest
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	void save_ReturnsPersistedProduct_WhenSuccessful() {
		Product expectedProduct = createFakeProductModel();
		
		Product productSaved = this.productRepository.save(expectedProduct);
		
		expectedProduct.setId(productSaved.getId());
		
		assertNotNull(productSaved);
		assertEquals(expectedProduct, productSaved);
	}
	
	@Test
	void save_ReturnsUpdatedProduct_WhenExistingProductIsEdited() {
		
		Product productSaved = this.productRepository.save(createFakeProductModel());
		
		Long oldId = productSaved.getId();
		String oldName = productSaved.getName();
		Integer oldAmount = productSaved.getAmount();
		
		Product productToBeUpdated = productSaved;
		productToBeUpdated.setName("Updated Product");
		productToBeUpdated.setAmount(1);
		
		Product updatedProduct = this.productRepository.save(productToBeUpdated);
		
		assertEquals(oldId, updatedProduct.getId());
		assertNotEquals(oldName, updatedProduct.getName());
		assertEquals("Updated Product", updatedProduct.getName());
		assertNotEquals(oldAmount, updatedProduct);
		assertEquals(1, updatedProduct.getAmount());
	}
	
	@Test
	void findById_ReturnsProduct_WhenSuccessful() {
		
		Product productSaved = this.productRepository.save(createFakeProductModel());
		
		Optional<Product> productReturned = this.productRepository.findById(productSaved.getId());
		
		assertEquals(true, productReturned.isPresent());
		assertEquals(productSaved, productReturned.get());
	}
	
	@Test
	void findById_ReturnsOptionalEmpty_WhenNotExistsId() {
		
		Product productSaved = this.productRepository.save(createFakeProductModel());
		
		Optional<Product> productReturned = this.productRepository.findById(productSaved.getId() + 100);
		
		assertEquals(false, productReturned.isPresent());
	}
	
	@Test
	void findAll_ReturnsListOfProducts_WhenSuccessful() {
		Product firstProduct = createFakeProductModel();
		Product secondProduct = createFakeProductModel();
		
		firstProduct.setName("First Product");
		secondProduct.setName("Second Product");
		secondProduct.setAmount(1);
		
		firstProduct = productRepository.save(firstProduct);
		secondProduct = productRepository.save(secondProduct);
		
		List<Product> products = this.productRepository.findAll();
		
		assertEquals(2, products.size());
		assertEquals(firstProduct, products.get(0));
		assertEquals(secondProduct, products.get(1));
	}
	
	@Test
	void findAll_ReturnsEmptyList_WhenNoProductExists() {
		
		List<Product> products = this.productRepository.findAll();
		
		assertEquals(true, products.isEmpty());
	}
	
	@Test
	void delete_ReturnsVoid_WhenSuccessful() {
		
		Product productSaved = this.productRepository.save(createFakeProductModel());
		
		this.productRepository.deleteById(productSaved.getId());
		
		Optional<Product> productReturned = this.productRepository.findById(productSaved.getId());
		
		assertEquals(false, productReturned.isPresent());
	}
	
}
