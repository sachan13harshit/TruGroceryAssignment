package com.trugroceryassignment.cart_api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.trugroceryassignment.cart_api.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CartApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartRepository cartRepository;

    @BeforeEach
    void setUp() {
        cartRepository.deleteAll();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void shouldReturnAllProducts() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10));
    }

    @Test
    void shouldAddItemToCart() throws Exception {
        String request = """
                {
                    "productId": 1,
                    "quantity": 2
                }
                """;
        mockMvc.perform(post("/cart/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.quantity").value(2));

    }
    @Test
    void shouldApplyFreeDeliveryThreshold() throws Exception {
        String request = """
                {
                    "productId": 6,
                    "quantity": 5
                }
                """;
        mockMvc.perform(post("/cart/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bill.itemTotalPaise").value(60000))
                .andExpect(jsonPath("$.bill.deliveryFeePaise").value(0))
                .andExpect(jsonPath("$.bill.grandTotalPaise").value(60000));
    }

    @Test
    void shouldReturnBadRequestForInvalidQuantity() throws Exception {
        String request = """
                {
                    "productId": 1,
                    "quantity": 0
                }
                """;
        mockMvc.perform(post("/cart/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }
    @Test
    void shouldReturnNotFoundForUnknownProduct() throws Exception {
        String request = """
                {
                    "productId": 100,
                    "quantity": 2
                }
                """;
        mockMvc.perform(post("/cart/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound());
    }
}