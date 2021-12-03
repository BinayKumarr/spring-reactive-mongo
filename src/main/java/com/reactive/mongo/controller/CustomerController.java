package com.reactive.mongo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.mongo.dto.CustomerDto;
import com.reactive.mongo.service.CustomerService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
@Slf4j
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping
	public Flux<CustomerDto> getCustomers() {
		log.info("Getting all Customer details using flux");
		return customerService.getCustomers();
	}
	
	@GetMapping("/{id}")
	public Mono<CustomerDto> getCustomer(@PathVariable String id) {
		log.info("Getting one Customer details using mono");
		return customerService.getCustomer(id);
	}
	
	@PostMapping
	public Mono<CustomerDto> saveCustomer(@RequestBody Mono<CustomerDto> customerDtoMono) {
		log.info("Saving customer details into Mongodb");
		return customerService.saveCustomer(customerDtoMono);
	}
	
	@PutMapping("/update/{id}")
	public Mono<CustomerDto> updateCustomer(@RequestBody Mono<CustomerDto> customerDtoMono,@PathVariable String id) {
		log.info("Updating customer details in Mongodb");
		return customerService.updateCustomer(customerDtoMono, id);
	}
	
	@DeleteMapping("/delete/{id}")
	public Mono<Void> deleteCustomer(@PathVariable String id) {
		return customerService.deleteCustomer(id);
	}

}
