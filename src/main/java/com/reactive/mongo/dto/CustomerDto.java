package com.reactive.mongo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor
@NoArgsConstructor

public class CustomerDto {
	private String id;
	private String name;
	private int totalQty;
	private double totalPrice;
	

}
