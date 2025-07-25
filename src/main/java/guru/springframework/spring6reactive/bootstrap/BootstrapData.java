package guru.springframework.spring6reactive.bootstrap;

import guru.springframework.spring6reactive.domain.Beer;
import guru.springframework.spring6reactive.domain.Customer;
import guru.springframework.spring6reactive.repository.BeerRepository;
import guru.springframework.spring6reactive.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class BootstrapData implements CommandLineRunner {

    @Autowired
    BeerRepository beerRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeer();
        loadCustomer();
        beerRepository.count().subscribe(count -> System.out.printf("Found %s beers%n", count));
        customerRepository.count().subscribe(count -> System.out.printf("Found %s customers%n", count));
    }

    private void loadBeer() {
        beerRepository.count().subscribe(count -> {
            if (count != 0) {
                return;
            }

            Beer beer1 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle("Pale Ale")
                    .upc("12356")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Crank")
                    .beerStyle("Pale Ale")
                    .upc("12356222")
                    .price(new BigDecimal("11.99"))
                    .quantityOnHand(392)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Sunshine City")
                    .beerStyle("IPA")
                    .upc("12356")
                    .price(new BigDecimal("13.99"))
                    .quantityOnHand(144)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1).subscribe();
            beerRepository.save(beer2).subscribe();
            beerRepository.save(beer3).subscribe();
        });
    }
    
    private void loadCustomer() {
        customerRepository.count().subscribe(count -> {
            if (count != 0) {
                return;
            }

            Customer customer1 = Customer.builder().customerName("Customer 1").build();
            Customer customer2 = Customer.builder().customerName("Customer 2").build();
            Customer customer3 = Customer.builder().customerName("Customer 3").build();

            customerRepository.save(customer1).subscribe();
            customerRepository.save(customer2).subscribe();
            customerRepository.save(customer3).subscribe();
        });
    }
}
