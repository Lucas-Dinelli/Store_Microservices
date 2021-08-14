package com.microservices.productcatalog.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.microservices.productcatalog.dto.ProductDTO;
import com.microservices.productcatalog.service.ProductService;

@RestController
@RequestMapping("/v1/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping
	public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO productDTO) {
		productDTO = productService.create(productDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(productDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(productDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		ProductDTO productDTO = productService.findById(id);
		return ResponseEntity.ok().body(productDTO);
	}
	
	@GetMapping
	public ResponseEntity<List<ProductDTO>> findAll() {
		return ResponseEntity.ok().body(productService.findAll());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTOUpdated){
		ProductDTO productDTO = productService.update(id, productDTOUpdated);
		return ResponseEntity.ok().body(productDTO);
	}
	
	@PutMapping("/{id}/amount")
	public ResponseEntity<ProductDTO> updateAmount(@PathVariable Long id, @RequestParam(value = "quantity", defaultValue = "0") Integer quantity){
		ProductDTO productDTO = productService.updateAmount(id, quantity);
		return ResponseEntity.ok().body(productDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		productService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
