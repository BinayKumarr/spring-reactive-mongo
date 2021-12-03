package com.reactive.mongo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.reactive.mongo.controller.CustomerController;
import com.reactive.mongo.dto.CustomerDto;
import com.reactive.mongo.service.CustomerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@RunWith(SpringRunner.class)
@WebFluxTest(CustomerController.class)
class SpringReactiveMongoApplicationTests {
	
	@Autowired
	private WebTestClient  webTestClient;
	
	@MockBean
	private CustomerService service;
	
	@Test
	public void addCustomerTest() {
		Mono<CustomerDto> customerDtoMono = Mono.just(new CustomerDto("102","TVCustomer",2,3000));
		when(service.saveCustomer(customerDtoMono)).thenReturn(customerDtoMono);
		
		webTestClient.post().uri("/customers")
		             .body(Mono.just(customerDtoMono),CustomerDto.class)
		             .exchange()
		             .expectStatus().isOk();
	}
	
	
	@Test  //Test case for getting all customer
	public void getCustomersTest() {
		Flux<CustomerDto> customerDtoFlux = Flux.just(new CustomerDto("203","LaptopCustomer",2,50000),
				new CustomerDto("204","DekstopCustomer",3,60000));
		
		when(service.getCustomers()).thenReturn(customerDtoFlux);
		
		Flux<CustomerDto> responseBody= webTestClient.get().uri("/customers")
								        .exchange()
								        .expectStatus().isOk()
								        .returnResult(CustomerDto.class)
								        .getResponseBody();
		
		StepVerifier.create(responseBody)
		.expectSubscription()
		.expectNext(new CustomerDto("203","LaptopCustomer",2,50000))
		.expectNext(new CustomerDto("204","DekstopCustomer",3,60000))
		.verifyComplete();		
		
	}
	
	@Test  //Test case for getting single Customer
	public void getCustomerTest() {
		Mono<CustomerDto> customerDtoMono = Mono.just(new CustomerDto("203","LaptopCustomer",2,50000));
		
		when(service.getCustomer(any())).thenReturn(customerDtoMono);
		
		Flux<CustomerDto> responseBody= webTestClient.get().uri("/customers/203")
		        .exchange()
		        .expectStatus().isOk()
		        .returnResult(CustomerDto.class)
		        .getResponseBody();
		
		StepVerifier.create(responseBody)
		.expectSubscription()
		.expectNextMatches(p->p.getName().equals("LaptopCustomer"))
		.verifyComplete();
	}
	
	@Test
	public void updateCustomerTest() {
		Mono<CustomerDto> customerDtoMono = Mono.just(new CustomerDto("203","LaptopCustomer",2,50000));
		when(service.updateCustomer(customerDtoMono, "203")).thenReturn(customerDtoMono);
		
		webTestClient.put().uri("/customers/update/203")
        .body(Mono.just(customerDtoMono),CustomerDto.class)
        .exchange()
        .expectStatus().isOk();
	}
	
	
	
	

}
