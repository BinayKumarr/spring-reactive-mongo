package com.reactive.mongo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.reactive.mongo.entity.Customer;

@Repository
public interface CustomerRepository  extends ReactiveMongoRepository<Customer, String>{

}
