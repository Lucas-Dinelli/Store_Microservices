package com.microservices.productcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservices.productcatalog.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
