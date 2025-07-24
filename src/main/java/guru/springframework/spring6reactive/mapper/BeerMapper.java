package guru.springframework.spring6reactive.mapper;

import guru.springframework.spring6reactive.domain.Beer;
import guru.springframework.spring6reactive.dto.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    BeerDTO toDto(Beer beer);

    Beer toEntity(BeerDTO beerDTO);
}
