package guru.springframework.spring6reactive.service;

import guru.springframework.spring6reactive.dto.BeerDTO;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.channels.FileChannel;

public interface BeerService {

    Flux<BeerDTO> listBeers();

    Mono<BeerDTO> getBeerById(Long beerId);

    Mono<BeerDTO> createBeer(BeerDTO beerDTO);

    Mono<BeerDTO> updateBeer(Long beerId, BeerDTO beerDTO);

    Mono<BeerDTO> patchBeer(Long beerId, BeerDTO beerDTO);

    Mono<Void> deleteBeerById(Long beerId);
}
