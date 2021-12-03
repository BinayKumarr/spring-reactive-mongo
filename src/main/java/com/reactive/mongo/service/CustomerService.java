package com.reactive.mongo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactive.mongo.dto.CustomerDto;
import com.reactive.mongo.repository.CustomerRepository;
import com.reactive.mongo.utils.AppUtils;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CustomerService {
 
	@Autowired
	private CustomerRepository customerRepository;
	
	public Flux<CustomerDto> getCustomers() {
		log.info("Getting all Customer details using flux");
		return customerRepository.findAll().map(AppUtils::entityToDto);
	}
	
	public Mono<CustomerDto> getCustomer(String id) {
		log.info("Getting one Customer using Mono");
		return customerRepository.findById(id).map(AppUtils::entityToDto);
	}
	
	public Mono<CustomerDto> saveCustomer(Mono<CustomerDto> customerDtoMono) {
		log.info("Inserting Customer details into MongoDB");
		return customerDtoMono.map(AppUtils::dtoToEntity)
		  .flatMap(customerRepository::insert)
		  .map(AppUtils::entityToDto);
	}
	
	public Mono<CustomerDto> updateCustomer(Mono<CustomerDto> customerDtoMono, String id) {
		log.info("Updating Customer details into MongoDB");
		   return  customerRepository.findById(id)// updating based on id
		    .flatMap(p->customerDtoMono.map(AppUtils::dtoToEntity))//converting dto to entity
		    .doOnNext(c->c.setId(id))// keeping id as it is
		    .flatMap(customerRepository::save) //saving data
		    .map(AppUtils::entityToDto);// last sending from entity to dto
	}
	
	public Mono<Void> deleteCustomer(String id) {
		log.info("Deleting Customer details from MongoDB");
		return customerRepository.deleteById(id);
	}
}
