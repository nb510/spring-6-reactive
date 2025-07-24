package guru.springframework.spring6reactive.service;

import guru.springframework.spring6reactive.dto.BeerDTO;
import guru.springframework.spring6reactive.mapper.BeerMapper;
import guru.springframework.spring6reactive.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public Flux<BeerDTO> listBeers() {
        return beerRepository.findAll().map(beerMapper::toDto);
    }

    @Override
    public Mono<BeerDTO> getBeerById(Long beerId) {
        return beerRepository.findById(beerId).map(beerMapper::toDto);
    }

    @Override
    public Mono<BeerDTO> createBeer(BeerDTO beerDTO) {
        return beerRepository.save(beerMapper.toEntity(beerDTO))
                .map(beerMapper::toDto);
    }

    @Override
    public Mono<BeerDTO> updateBeer(Long beerId, BeerDTO beerDTO) {
        return beerRepository.findById(beerId)
                .map(beer -> {
                    beer.setBeerName(beerDTO.getBeerName());
                    beer.setBeerStyle(beerDTO.getBeerStyle());
                    beer.setPrice(beerDTO.getPrice());
                    beer.setUpc(beerDTO.getUpc());
                    beer.setQuantityOnHand(beerDTO.getQuantityOnHand());
                    return beer;
                }).flatMap(beerRepository::save)
                .map(beerMapper::toDto);
    }

    @Override
    public Mono<BeerDTO> patchBeer(Long beerId, BeerDTO beerDTO) {
        return beerRepository.findById(beerId)
                .map(beer -> {
                    if (beerDTO.getBeerName() != null) {
                        beer.setBeerName(beerDTO.getBeerName());
                    }
                    if (beerDTO.getBeerStyle() != null) {
                        beer.setBeerStyle(beerDTO.getBeerStyle());
                    }
                    if (beerDTO.getPrice() != null) {
                        beer.setPrice(beerDTO.getPrice());
                    }
                    if (beerDTO.getUpc() != null) {
                        beer.setUpc(beerDTO.getUpc());
                    }
                    if (beerDTO.getQuantityOnHand() != null) {
                        beer.setQuantityOnHand(beerDTO.getQuantityOnHand());
                    }
                    return beer;
                }).flatMap(beerRepository::save)
                .map(beerMapper::toDto);
    }

    @Override
    public Mono<Void> deleteBeerById(Long beerId) {
        return beerRepository.deleteById(beerId);
    }
}
