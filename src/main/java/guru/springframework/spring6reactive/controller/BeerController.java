package guru.springframework.spring6reactive.controller;

import guru.springframework.spring6reactive.domain.Beer;
import guru.springframework.spring6reactive.dto.BeerDTO;
import guru.springframework.spring6reactive.mapper.BeerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class BeerController {

    public static final String BEER_PATH = "/api/v2/beer";

    private final BeerMapper beerMapper;

    @GetMapping(BEER_PATH)
    public Flux<BeerDTO> listBeers() {
        return Flux.just(
                beerMapper.toDto(Beer.builder().id(1).build()),
                beerMapper.toDto(Beer.builder().id(2).build()));
    }
}
