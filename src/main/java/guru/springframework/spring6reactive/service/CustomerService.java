package guru.springframework.spring6reactive.service;

import guru.springframework.spring6reactive.dto.CustomerDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface CustomerService {

    Flux<CustomerDTO> listCustomers();

    Mono<CustomerDTO> getCustomerById(Long customerId);

    Mono<CustomerDTO> createCustomer(CustomerDTO customerDTO);

    Mono<CustomerDTO> updateCustomer(Long customerId, CustomerDTO customerDTO);

    Mono<CustomerDTO> patchCustomer(Long customerId, CustomerDTO customerDTO);

    Mono<Void> deleteCustomerById(Long customerId);
}
