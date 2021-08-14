package com.microservices.shoppingcart.transactions.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

	private String name;
    
    @JsonProperty(access = Access.WRITE_ONLY)
    private Integer amount;
    
    public boolean isNewAmountOk(Integer quantity) {
    	if(this.getAmount() < 0) {
    		return false;
    	}
    	else if((this.getAmount() - quantity) >= 0) {  
    		return true;
    	}
    	else {
    		this.setName("The amount entered is not currently available");
    		return false;
    	}
    }
}
