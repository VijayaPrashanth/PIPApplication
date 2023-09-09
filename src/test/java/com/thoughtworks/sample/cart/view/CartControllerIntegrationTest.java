package com.thoughtworks.sample.cart.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.sample.SampleApplication;
import com.thoughtworks.sample.cart.CartService;
import com.thoughtworks.sample.cart.repository.Cart;
import com.thoughtworks.sample.cart.repository.CartRepository;
import com.thoughtworks.sample.exception.ItemNotFoundException;
import com.thoughtworks.sample.inventory.repository.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @Mock
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
    public void shouldAddItemsToCart() throws Exception, ItemNotFoundException {
        Inventory inventory = new Inventory("onion",new BigDecimal(40),"1KG");
        Cart item = new Cart(inventory,"onion", 2, "KG");
        String cartJson = new ObjectMapper().writeValueAsString(item);
        List<Cart> itemInCart = Arrays.asList(item);

        when(cartService.addItems(any(Cart.class))).thenReturn(itemInCart);


        mockMvc.perform(MockMvcRequestBuilders.put("/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cartJson))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldAddEditedItemsToCart() throws Exception, ItemNotFoundException {
        Inventory inventory = new Inventory("onion",new BigDecimal(40),"1KG");
        Cart item = new Cart(inventory,"onion", 2, "KG");
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
        Inventory inventory1 = new Inventory("onion",new BigDecimal(40),"1KG");
        Cart item1 = new Cart(inventory1,"onion", 2, "KG");
        Inventory inventory2 = new Inventory("tomato",new BigDecimal(20),"1KG");
        Cart item2 = new Cart(inventory2,"tomato", 2, "1KG");
        List<Cart> cartItems = Arrays.asList(item1, item2);
        String response = new ObjectMapper().writeValueAsString(cartItems);

        when(cartService.getItems()).thenReturn(cartItems);

        mockMvc.perform(MockMvcRequestBuilders.get("/cart")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(response))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void shouldDeleteItemFromCartById() throws ItemNotFoundException,Exception {

        int id=1;
        when(cartService.deleteItem(id)).thenReturn("Item removed from the cart");

        mockMvc.perform(delete("/cart/{id}",id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Item removed from the cart"));
    }

    @Test
    public void shouldThrowExceptionWhenInvalidIdIsGiven() throws ItemNotFoundException, Exception {
        int id=1;
        when(cartService.deleteItem(id)).thenThrow(new ItemNotFoundException());

        mockMvc.perform(delete("/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturnMessageWhenValidIsGivenForDeletion() throws Exception, ItemNotFoundException {
        int id =1;
        //when(cartRepository.existsById(id)).thenReturn(true);
        when(cartService.deleteItemByInventoryId(id)).thenReturn("Item removed from cart as it is modified in inventory");

        mockMvc.perform(delete("/cart/byInventory/{id}",id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("Item removed from cart as it is modified in inventory"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnMessageWhenInvalidIdIsGivenForDeletion() throws Exception {
        int id =16;
        when(cartService.deleteItemByInventoryId(id)).thenReturn("This Item is not present in cart");

        mockMvc.perform(delete("/cart/byInventory/{id}",id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("This Item is not present in cart"))
                .andExpect(status().isOk());
    }
}
