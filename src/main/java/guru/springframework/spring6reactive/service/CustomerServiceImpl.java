package guru.springframework.spring6reactive.service;

import guru.springframework.spring6reactive.dto.CustomerDTO;
import guru.springframework.spring6reactive.mapper.CustomerMapper;
import guru.springframework.spring6reactive.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Flux<CustomerDTO> listCustomers() {
        return customerRepository.findAll().map(customerMapper::toDTO);
    }

    @Override
    public Mono<CustomerDTO> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).map(customerMapper::toDTO);
    }

    @Override
    public Mono<CustomerDTO> createCustomer(CustomerDTO customerDTO) {
        return customerRepository.save(customerMapper.toEntity(customerDTO)).map(customerMapper::toDTO);
    }

    @Override
    public Mono<CustomerDTO> updateCustomer(Long customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId)
                .map(customer -> {
                    customer.setCustomerName(customerDTO.getCustomerName());
                    return customer;
                }).flatMap(customerRepository::save)
                .map(customerMapper::toDTO);
    }

    @Override
    public Mono<CustomerDTO> patchCustomer(Long customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId)
                .map(customer -> {
                    if (customerDTO.getCustomerName() != null) {
                        customer.setCustomerName(customerDTO.getCustomerName());
                    }
                    return customer;
                }).flatMap(customerRepository::save)
                .map(customerMapper::toDTO);
    }

    @Override
    public Mono<Void> deleteCustomerById(Long customerId) {
        return customerRepository.deleteById(customerId);
    }
}
