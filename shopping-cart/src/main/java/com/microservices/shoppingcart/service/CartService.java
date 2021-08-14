package com.microservices.shoppingcart.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.shoppingcart.client.ProductClient;
import com.microservices.shoppingcart.dto.CartDTO;
import com.microservices.shoppingcart.model.Cart;
import com.microservices.shoppingcart.model.Item;
import com.microservices.shoppingcart.repository.CartRepository;
import com.microservices.shoppingcart.service.exceptions.ObjectNotFoundException;
import com.microservices.shoppingcart.transactions.model.Product;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CartService {
	
	private CartRepository cartRepository;
	
	private ModelMapper modelMapper;
	
	private ProductClient productClient;
	
	public CartDTO create(CartDTO cartDTO) {
		Cart cart = modelMapper.map(cartDTO, Cart.class);
		
		boolean isListItemsOk = this.verifyListOfItems(cart.getItems());
		
		if(isListItemsOk) {
			cartDTO = modelMapper.map(cartRepository.save(cart), CartDTO.class);
		}
		return cartDTO;
	}
	
	public CartDTO findById(Long id) {
		Optional<Cart> optionalCart = cartRepository.findById(id);
		CartDTO cartDTO = modelMapper.map(optionalCart.orElseThrow(() -> new ObjectNotFoundException("Cart not found!")), CartDTO.class);
		
		optionalCart.get().getItems().stream().forEach(item -> {
			Product product = productClient.findById(item.getProductId()).getBody();
			item.setProduct(product);
		});
		
		return cartDTO;
	}
	
	public List<CartDTO> findAll(){
		List<Cart> carts = cartRepository.findAll();
		carts.stream().forEach(cart -> cart.getItems().stream().forEach(item -> {
			Product product = productClient.findById(item.getProductId()).getBody();
			item.setProduct(product);
		}));
		return carts.stream().map(cart -> modelMapper.map(cart, CartDTO.class)).collect(Collectors.toList());
	}
	
	public void delete(Long id) {
		findById(id);
		cartRepository.deleteById(id);
	}
	
	private boolean verifyListOfItems(List<Item> items) {
		if(items.stream()
				.map(item -> item.setProduct(productClient.findById(item.getProductId()).getBody()))
				.allMatch(item -> item.getProduct().isNewAmountOk(item.getAmount())) && !isExistingEqualsProductsId(items)) {
			items.forEach(item -> {
				Product updatedProduct = productClient.updateAmount(item.getProductId(), item.getAmount()).getBody();
				item.setProduct(updatedProduct);
			});
			return true;
		}
		else {
			this.changeTheUnsuccessfully(items, "The amount", "Unsuccessfully!");
			return false;
		}
	}
	
	private boolean isExistingEqualsProductsId(List<Item> items) {
		List<Long> productsId = items.stream().map(item -> item.getProductId()).collect(Collectors.toList());
		if(productsId.stream()
				.map(productId -> Collections.frequency(productsId, productId))
				.anyMatch(frequency -> frequency > 1)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private void changeTheUnsuccessfully(List<Item> items, String firstWordsOfMessage, String newName) {
		for(Item item : items) {
			if(Optional.ofNullable(item.getProduct()).isPresent()) {
				if(!(item.getProduct().getName().startsWith(firstWordsOfMessage))) {
					item.setProduct(Product.builder().name("Unsuccessfully!").build());
				}
			}
			else if(Optional.ofNullable(item.getProduct()).isEmpty()) {
				item.setProduct(Product.builder().name(newName).build());
			}
		}
	}

}
