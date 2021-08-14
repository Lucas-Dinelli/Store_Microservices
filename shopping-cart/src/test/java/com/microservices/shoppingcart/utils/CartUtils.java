package com.microservices.shoppingcart.utils;

import java.util.Collections;

import com.microservices.shoppingcart.dto.CartDTO;
import com.microservices.shoppingcart.model.Cart;

public class CartUtils {
	
	private static final Long CART_ID = 1L;
	
	public static CartDTO createFakeCartDTO() {
    	return CartDTO.builder()
    			.id(CART_ID)
    			.items(Collections.singletonList(ItemUtils.createFakeItemModel()))
    			.build();
    }
    
    public static Cart createFakeCartModel() {
        return Cart.builder()
                .id(CART_ID)
                .items(Collections.singletonList(ItemUtils.createFakeItemModel()))
                .build();
    }

}
