package guru.springframework.spring6reactive.mapper;

import guru.springframework.spring6reactive.domain.Customer;
import guru.springframework.spring6reactive.dto.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    CustomerDTO toDTO(Customer customer);

    Customer toEntity(CustomerDTO customerDTO);
}
