package com.microservices.shoppingcart.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.microservices.shoppingcart.model.Item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {
	
	private Long id;
	
	@NotNull(message = "The items must be informated")
	@Valid
	private List<Item> items;

}
