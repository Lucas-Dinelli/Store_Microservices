package com.microservices.shoppingcart.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.microservices.shoppingcart.model.Cart;

import static com.microservices.shoppingcart.utils.CartUtils.createFakeCartModel;

@DataJpaTest
public class CartRepositoryTest {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Test
	void save_ReturnsPersistedCart_WhenSuccessful() {
		Cart expectedCart = createFakeCartModel();
		expectedCart.getItems().get(0).setProduct(null);
		
		Cart cartSaved = this.cartRepository.save(expectedCart);
		
		expectedCart.setId(cartSaved.getId());
		expectedCart.getItems().get(0).setId(cartSaved.getItems().get(0).getId());
		
		assertNotNull(cartSaved);
		assertEquals(expectedCart, cartSaved);
	}
	
	@Test
	void save_ReturnsConstraintViolationException_WhenTheAmountIsLessThanMinimum() {
		Cart expectedCart = createFakeCartModel();
		expectedCart.getItems().get(0).setAmount(0);
		
		assertThrows(ConstraintViolationException.class, () -> this.cartRepository.save(expectedCart));
	}
	
	@Test
	void findById_ReturnsCart_WhenSuccessful() {
		
		Cart cartSaved = this.cartRepository.save(createFakeCartModel());
		
		Optional<Cart> cartReturned = this.cartRepository.findById(cartSaved.getId());
		
		assertEquals(true, cartReturned.isPresent());
		assertEquals(cartSaved, cartReturned.get());
	}
	
	@Test
	void findById_ReturnsOptionalEmpty_WhenNotExistsId() {
		
		Cart cartSaved = this.cartRepository.save(createFakeCartModel());
		
		Optional<Cart> cartReturned = this.cartRepository.findById(cartSaved.getId() + 100);
		
		assertEquals(false, cartReturned.isPresent());
	}
	
	@Test
	void findAll_ReturnsListOfCarts_WhenSuccessful() {
		Cart firstCart = createFakeCartModel();
		Cart secondCart = createFakeCartModel();
		
		firstCart.getItems().get(0).setAmount(15);
		secondCart.getItems().get(0).setAmount(20);
		
		firstCart = cartRepository.save(firstCart);
		secondCart = cartRepository.save(secondCart);
		
		List<Cart> carts = this.cartRepository.findAll();
		
		assertEquals(2, carts.size());
		assertEquals(firstCart, carts.get(0));
		assertEquals(secondCart, carts.get(1));
	}
	
	@Test
	void findAll_ReturnsEmptyList_WhenNoCartExists() {
		
		List<Cart> carts = this.cartRepository.findAll();
		
		assertEquals(true, carts.isEmpty());
	}
	
	@Test
	void delete_ReturnsVoid_WhenSuccessful() {
		
		Cart cartSaved = this.cartRepository.save(createFakeCartModel());
		
		this.cartRepository.deleteById(cartSaved.getId());
		
		Optional<Cart> cartReturned = this.cartRepository.findById(cartSaved.getId());
		
		assertEquals(false, cartReturned.isPresent());
	}
	
}
