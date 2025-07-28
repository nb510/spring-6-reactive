package guru.springframework.spring6reactive.controller;

import guru.springframework.spring6reactive.dto.BeerDTO;
import guru.springframework.spring6reactive.service.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class BeerController {

    public static final String BEER_PATH = "/api/v2/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    @GetMapping(BEER_PATH)
    public Flux<BeerDTO> listBeers() {
        return beerService.listBeers();
    }

    @GetMapping(BEER_PATH_ID)
    public Mono<BeerDTO> getBeerById(@PathVariable("beerId") Long beerId) {
        return beerService.getBeerById(beerId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping(BEER_PATH)
    public Mono<ResponseEntity<Void>> createBeer(@Validated @RequestBody BeerDTO beerDTO) {
        return beerService.createBeer(beerDTO)
                .map(savedBeer -> ResponseEntity.created(UriComponentsBuilder
                                .fromUriString("http://localhost:8080/api/v2/beer/%s".formatted(savedBeer.getId()))
                                .build()
                                .toUri()
                        ).build()
                );
    }

    @PutMapping(BEER_PATH_ID)
    public Mono<ResponseEntity<Void>> updateBeer(@PathVariable("beerId") Long beerId, @RequestBody BeerDTO beerDTO) {
        return beerService.updateBeer(beerId, beerDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(beer -> ResponseEntity.noContent().build());
    }

    @PatchMapping(BEER_PATH_ID)
    public Mono<ResponseEntity<Void>> patchBeer(@PathVariable("beerId") Long beerId, @RequestBody BeerDTO beerDTO) {
        return beerService.patchBeer(beerId, beerDTO)
                .map(beer -> ResponseEntity.ok().build());
    }

    @DeleteMapping(BEER_PATH_ID)
    public Mono<ResponseEntity<Void>> deleteBeerById(@PathVariable("beerId") Long beerId) {
        return beerService.deleteBeerById(beerId).thenReturn(ResponseEntity.noContent().build());
    }
}
