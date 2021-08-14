package com.microservices.shoppingcart.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.microservices.shoppingcart.transactions.model.Product;

@Component
public class ProductHystrixFallbackFactory implements ProductClient {

	@Override
	public ResponseEntity<Product> findById(Long id) {
		Product noneProduct = Product.builder().name("Unsuccessfully!").amount(-1).build();
		return ResponseEntity.ok().body(noneProduct);
	}

	@Override
	public ResponseEntity<Product> updateAmount(Long id, Integer quantity) {
		id = -1L;
		Product noneProduct = Product.builder().name("Unsuccessfully!").amount(0).build();
		return ResponseEntity.ok().body(noneProduct);
	}

}
