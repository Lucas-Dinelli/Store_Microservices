package com.microservices.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservices.shoppingcart.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
