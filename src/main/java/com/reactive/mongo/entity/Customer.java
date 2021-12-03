package com.reactive.mongo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customers")
public class Customer {
	@Id
	private String id;
	private String name;
	private int totalQty;
	private double totalPrice;

}
