package guru.springframework.spring6reactive.controller;

import guru.springframework.spring6reactive.dto.CustomerDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static guru.springframework.spring6reactive.controller.CustomerController.CUSTOMER_PATH;
import static guru.springframework.spring6reactive.controller.CustomerController.CUSTOMER_PATH_ID;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(999)
    void testDeleteCustomer() {
        webTestClient.mutateWith(mockOAuth2Login())
                .delete().uri(CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(1)
    void testUpdateCustomer() {
        webTestClient.mutateWith(mockOAuth2Login())
                .put().uri(CUSTOMER_PATH_ID, 1)
                .body(Mono.just(getTestCustomer()), CustomerDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testCreateCustomer() {
        webTestClient.mutateWith(mockOAuth2Login())
                .post().uri(CUSTOMER_PATH)
                .body(Mono.just(getTestCustomer()), CustomerDTO.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/customer/4");
    }

    @Test
    void testGetCustomerById() {
        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri(CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerDTO.class);
    }

    @Test
    void testListCustomers() {
        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri(CUSTOMER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.size()").isEqualTo(2);
    }

    public static CustomerDTO getTestCustomer() {
        return CustomerDTO.builder().customerName("Customer test").build();
    }

}