package com.microservices.shoppingcart.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.microservices.shoppingcart.dto.CartDTO;
import com.microservices.shoppingcart.service.CartService;

@RestController
@RequestMapping("/v1/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@PostMapping
	public ResponseEntity<CartDTO> create(@Valid @RequestBody CartDTO cartDTO) {
		cartDTO = cartService.create(cartDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cartDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(cartDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CartDTO> findById(@PathVariable Long id) {
		CartDTO cartDTO = cartService.findById(id);
		return ResponseEntity.ok().body(cartDTO);
	}
	
	@GetMapping
	public ResponseEntity<List<CartDTO>> findAll() {
		return ResponseEntity.ok().body(cartService.findAll());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		cartService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
