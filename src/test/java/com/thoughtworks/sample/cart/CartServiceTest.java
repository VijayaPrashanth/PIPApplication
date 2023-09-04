package com.thoughtworks.sample.cart;

import com.thoughtworks.sample.cart.repository.Cart;
import com.thoughtworks.sample.cart.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void shouldAddItemsToCart() {
        Cart item = new Cart("onion",2);

        when(cartRepository.save(any(Cart.class))).thenReturn(item);
        //when(cartService.addItems(any(Cart.class))).thenReturn(item);

        Cart responseItem = cartService.addItems(item);
        assertEquals(responseItem,cartService.addItems(item));
    }
}
