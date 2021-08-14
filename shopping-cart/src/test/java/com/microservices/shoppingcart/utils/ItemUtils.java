package com.microservices.shoppingcart.utils;

import com.microservices.shoppingcart.model.Item;
import com.microservices.shoppingcart.transactions.model.Product;

public class ItemUtils {
	
	private static final Long ITEM_ID = 1L;
	private static final Long PRODUCT_ID = 1L;
	private static final Integer AMOUNT = 10;
	
	public static Item createFakeItemModel() {
		return Item.builder()
				.id(ITEM_ID)
				.productId(PRODUCT_ID)
				.amount(AMOUNT)
				.product(Product.builder().name("Test Product").amount(50).build())
				.build();
	}
	
}
