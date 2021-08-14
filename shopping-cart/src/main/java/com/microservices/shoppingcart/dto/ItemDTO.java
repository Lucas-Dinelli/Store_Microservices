/*package com.microservices.shoppingcart.dto;

import javax.validation.constraints.NotNull;

import com.microservices.shoppingcart.transactions.model.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {
	
	private Long id;
	
	@NotNull(message = "The productId field must be filled")
	private Long productId;
	
	@NotNull(message = "The amount field must be filled")
    private Integer amount;
	
	private Product product;
	
}*/
