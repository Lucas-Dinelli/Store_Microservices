package com.microservices.productcatalog.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
	
	private Long id;
	
	@NotBlank(message = "The name field must be filled")
	private String name;
	
	@NotNull(message = "The amount field must be filled")
	@Min(value = 0, message = "The amount of products cannot be less than zero")
    private Integer amount;
	
}
