package com.thoughtworks.sample.cart;

import com.thoughtworks.sample.cart.repository.Cart;
import com.thoughtworks.sample.cart.repository.CartRepository;
import com.thoughtworks.sample.exception.ItemNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

        Cart item = new Cart("onion", 2, "KG");
        List<Cart> itemList = Arrays.asList(item);

        when(cartRepository.getItemDetails()).thenReturn(itemList);
        List<Cart> itemsFromService = cartService.addItems(item);

        verify(cartRepository).save(item);
        assertNotNull(itemsFromService);
        assertEquals(itemList, itemsFromService);
    }

    @Test
    public void shouldGetItemsFromCart() {

        Cart item1 = new Cart("onion", 2, "KG");
        Cart item2 = new Cart("apple", 3, "KG");
        List<Cart> cartItems = Arrays.asList(item1, item2);

        when(cartRepository.getItemDetails()).thenReturn(cartItems);
        List<Cart> itemsFromService = cartService.getItems();

        verify(cartRepository).getItemDetails();
        assertNotNull(itemsFromService);
        assertEquals(2, itemsFromService.size());
        assertEquals(cartItems, itemsFromService);
    }

    @Test
    public void shouldDeleteItemFromCart() throws ItemNotFoundException {

        int itemIdToDelete = 1;

        when(cartRepository.existsById(itemIdToDelete)).thenReturn(true);
        String result = cartService.deleteItem(itemIdToDelete);

        verify(cartRepository).existsById(itemIdToDelete);
        verify(cartRepository).deleteById(itemIdToDelete);
        assertEquals("Item removed from the cart", result);
    }

    @Test()
    public void shouldThrowExceptionWhenDeletingNonExistentItem() throws ItemNotFoundException {
        int itemIdToDelete = 1;

        when(cartRepository.existsById(itemIdToDelete)).thenReturn(false);

        assertThrows(ItemNotFoundException.class,()-> cartService.deleteItem(itemIdToDelete));

    }
}
