package com.microservices.shoppingcart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.microservices.shoppingcart.transactions.model.Product;

@FeignClient(name="product-catalog", fallback = ProductHystrixFallbackFactory.class)
public interface ProductClient {
	
	@GetMapping("/v1/product/{id}")
	public ResponseEntity<Product> findById(@PathVariable("id") Long id);
	
	@PutMapping("/v1/product/{id}/amount")
	public ResponseEntity<Product> updateAmount(@PathVariable("id") Long id, @RequestParam(value = "quantity", defaultValue = "0") Integer quantity);

}
