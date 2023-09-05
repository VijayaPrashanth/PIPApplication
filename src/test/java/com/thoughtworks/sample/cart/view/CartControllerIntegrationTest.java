package com.thoughtworks.sample.cart.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.sample.SampleApplication;
import com.thoughtworks.sample.cart.CartService;
import com.thoughtworks.sample.cart.repository.Cart;
import com.thoughtworks.sample.cart.repository.CartRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SampleApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@WithMockUser
public class CartControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @Autowired
    private CartRepository cartRepository;

    @BeforeEach
    public void beforeEachSetup() {
        MockitoAnnotations.openMocks(this);
        cartRepository.deleteAll();
    }

    @AfterEach
    public void afterEachSetup() {
        cartRepository.deleteAll();
    }

    @Test
    public void shouldAddItemsToCart() throws Exception {
        Cart item = new Cart("onion",2,"KG");
        String cartJson = new ObjectMapper().writeValueAsString(item);
        List<Cart> itemInCart = Arrays.asList(item);

        when(cartService.addItems(any(Cart.class))).thenReturn(itemInCart);


        mockMvc.perform(MockMvcRequestBuilders.put("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cartJson))
                .andExpect(status().isOk());

    }


    @Test
    public void shouldGetItemsFromCart() throws Exception {
        Cart item1 = new Cart("onion", 2, "KG");
        Cart item2 = new Cart("apple", 3, "KG");
        List<Cart> cartItems = Arrays.asList(item1, item2);

        when(cartService.getItems()).thenReturn(cartItems);

        mockMvc.perform(MockMvcRequestBuilders.get("/cart")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
