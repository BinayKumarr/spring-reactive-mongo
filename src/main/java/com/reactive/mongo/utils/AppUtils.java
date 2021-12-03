package com.reactive.mongo.utils;

import org.springframework.beans.BeanUtils;

import com.reactive.mongo.dto.CustomerDto;
import com.reactive.mongo.entity.Customer;

public class AppUtils {

	public static CustomerDto entityToDto(Customer customer) {
		CustomerDto customerDto = new CustomerDto();
		BeanUtils.copyProperties(customer, customerDto);
		return customerDto;
	}
	
	public static Customer dtoToEntity(CustomerDto customerDto) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerDto, customer);
		return customer;
	}
}
