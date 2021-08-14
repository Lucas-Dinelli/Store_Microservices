package com.microservices.productcatalog.utils;

import com.microservices.productcatalog.dto.ProductDTO;
import com.microservices.productcatalog.model.Product;

public class ProductUtils {
	
	private static final Long PRODUCT_ID = 1L;
	private static final String NAME = "Test Product";
	private static final Integer AMOUNT = 10;
	
	public static ProductDTO createFakeProductDTO() {
		return ProductDTO.builder()
				.id(PRODUCT_ID)
				.name(NAME)
				.amount(AMOUNT)
				.build();
	}
	
	public static Product createFakeProductModel() {
		return Product.builder()
				.id(PRODUCT_ID)
				.name(NAME)
				.amount(AMOUNT)
				.build();
	}

}
