package com.microservices.shoppingcart.service;

import static com.microservices.shoppingcart.utils.CartUtils.createFakeCartDTO;
import static com.microservices.shoppingcart.utils.CartUtils.createFakeCartModel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import com.microservices.shoppingcart.client.ProductClient;
import com.microservices.shoppingcart.dto.CartDTO;
import com.microservices.shoppingcart.model.Cart;
import com.microservices.shoppingcart.repository.CartRepository;
import com.microservices.shoppingcart.service.exceptions.ObjectNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
	
	@InjectMocks
	private CartService cartService;
	
	@Mock
	private CartRepository cartRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private ProductClient productClient;
	
	@Test
	void create_ReturnsCartDTO_WhenSuccessful() {
		CartDTO cartDTO = createFakeCartDTO();
		Cart expectedCart = createFakeCartModel();
        
        when(modelMapper.map(cartDTO, Cart.class)).thenReturn(expectedCart);
        
        when(productClient.findById(any(Long.class)))
		.thenReturn(ResponseEntity.ok().body(expectedCart.getItems().get(0).getProduct()));
        
        when(productClient.updateAmount(any(Long.class), any(Integer.class)))
			.thenReturn(ResponseEntity.ok().body(expectedCart.getItems().get(0).getProduct()));
        
        when(cartRepository.save(any(Cart.class))).thenReturn(expectedCart);
        
        when(modelMapper.map(expectedCart, CartDTO.class)).thenReturn(cartDTO);
        
        cartDTO = cartService.create(cartDTO);
        
        assertEquals(1L, expectedCart.getId());
        assertEquals(expectedCart.getItems(), cartDTO.getItems());
	}
	
	@Test
	void findById_ReturnsCartDTO_WhenSuccessful() {
		CartDTO cartDTO = createFakeCartDTO();
		Cart expectedCart = createFakeCartModel();
		
		when(cartRepository.findById(expectedCart.getId())).thenReturn(Optional.of(expectedCart));
		
		when(modelMapper.map(expectedCart, CartDTO.class)).thenReturn(cartDTO);
		
		when(productClient.findById(any(Long.class)))
			.thenReturn(ResponseEntity.ok().body(expectedCart.getItems().get(0).getProduct()));
		
		cartDTO = cartService.findById(1L);
		
        assertEquals(expectedCart.getItems(), cartDTO.getItems());
	}
	
	@Test
	void findById_ThrowsObjectNotFoundException_WhenCartIsEmpty() {
		
		assertThrows(ObjectNotFoundException.class, () -> cartService.findById(1L));
	}
	
	@Test
	void findAll_ReturnsListOfCartDTO_WhenSuccessful() {
		List<CartDTO> cartsDTO = Collections.singletonList(createFakeCartDTO());
		List<Cart> expectedCarts = Collections.singletonList(createFakeCartModel());

		when(cartRepository.findAll()).thenReturn(expectedCarts);
		
		when(productClient.findById(any(Long.class)))
			.thenReturn(ResponseEntity.ok().body(expectedCarts.get(0).getItems().get(0).getProduct()));
		
		when(modelMapper.map(expectedCarts.get(0), CartDTO.class)).thenReturn(cartsDTO.get(0));
		
		cartsDTO = cartService.findAll();
		
		assertEquals(cartsDTO.size(), 1);
		assertEquals(expectedCarts.get(0).getId(), cartsDTO.get(0).getId());
		assertEquals(expectedCarts.get(0).getItems(), cartsDTO.get(0).getItems());
	}
	
	@Test
	void findAll_ReturnsEmptyList_WhenNoCartExists() {
		List<CartDTO> cartsDTO;
		List<Cart> expectedCarts = Collections.emptyList();
		
		when(cartRepository.findAll()).thenReturn(expectedCarts);
		
		cartsDTO = cartService.findAll();
		
		assertEquals(0, cartsDTO.size());
	}
	
	@Test
	void delete_ReturnsVoid_WhenSuccessful() {
		Cart expectedCart = createFakeCartModel();
		
		when(cartRepository.findById(1L)).thenReturn(Optional.of(expectedCart));
		
		when(productClient.findById(any(Long.class)))
			.thenReturn(ResponseEntity.ok().body(expectedCart.getItems().get(0).getProduct()));
		
		doNothing().when(cartRepository).deleteById(expectedCart.getId());
		
		cartService.delete(1L);
	}

}
