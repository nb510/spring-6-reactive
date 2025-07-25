package guru.springframework.spring6reactive.controller;

import guru.springframework.spring6reactive.dto.CustomerDTO;
import guru.springframework.spring6reactive.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v2/customer";
    public static final String CUSTOMER_PATH_ID = "/api/v2/customer/{customerId}";

    private final CustomerService customerService;

    @GetMapping(CUSTOMER_PATH)
    public Flux<CustomerDTO> listCustomers() {
        return customerService.listCustomers();
    }

    @GetMapping(CUSTOMER_PATH_ID)
    public Mono<CustomerDTO> getCustomerById(@PathVariable("customerId") Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    @PostMapping(CUSTOMER_PATH)
    public Mono<ResponseEntity<Void>> createCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.createCustomer(customerDTO)
                .map(createdCustomer -> ResponseEntity.created(UriComponentsBuilder
                                .fromUriString("http://localhost:8080/api/v2/customer/%s".formatted(createdCustomer.getId()))
                                .build()
                                .toUri()
                        ).build()
                );
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public Mono<ResponseEntity<Void>> updateCustomer(@PathVariable("customerId") Long customerId,
                                                     @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(customerId, customerDTO)
                .map(response -> ResponseEntity.noContent().build());
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public Mono<ResponseEntity<Void>> patchCustomer(@PathVariable("customerId") Long customerId,
                                                     @RequestBody CustomerDTO customerDTO) {
        return customerService.patchCustomer(customerId, customerDTO)
                .map(response -> ResponseEntity.ok().build());
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable("customerId") Long customerId) {
        return customerService.deleteCustomerById(customerId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
