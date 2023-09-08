package com.thoughtworks.sample.cart;

import com.thoughtworks.sample.cart.repository.Cart;
import com.thoughtworks.sample.cart.repository.CartRepository;
import com.thoughtworks.sample.exception.ItemNotFoundException;
import com.thoughtworks.sample.inventory.repository.Inventory;
import com.thoughtworks.sample.inventory.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void shouldAddItemsToCart() throws ItemNotFoundException {

        Inventory inventory = new Inventory("onion",new BigDecimal(40),"1KG");
        Cart item1 = new Cart(inventory,"onion", 2, "1KG");
        List<Cart> cartItems = Arrays.asList(item1);

        when(inventoryRepository.existsById(item1.getId())).thenReturn(true);
        when(inventoryRepository.findById(item1.getId())).thenReturn(Optional.of(inventory));
        when(cartRepository.getItemDetails()).thenReturn(cartItems);
        List<Cart> itemsFromService = cartService.addItems(item1);


        verify(cartRepository).getItemDetails();
        assertNotNull(itemsFromService);
        assertEquals(cartItems, itemsFromService);
    }

    @Test
    public void shouldAddEditedItemsToCart() throws ItemNotFoundException {

        Inventory inventory = new Inventory("onion",new BigDecimal(40),"1KG");
        Cart item1 = new Cart(inventory,"onion", 2, "1KG");
        List<Cart> cartItems = Arrays.asList(item1);

        when(inventoryRepository.existsById(item1.getId())).thenReturn(true);
        when(inventoryRepository.findById(item1.getId())).thenReturn(Optional.of(inventory));
        when(cartRepository.getItemDetails()).thenReturn(cartItems);
        List<Cart> itemsFromService = cartService.addItems(item1);


        verify(cartRepository).getItemDetails();
        assertNotNull(itemsFromService);
        assertEquals(cartItems, itemsFromService);
    }

    @Test
    public void shouldGetItemsFromCart() {

        Inventory inventory = new Inventory("onion",new BigDecimal(40),"1KG");
        Cart item1 = new Cart(inventory,"onion", 2, "KG");
        Inventory inventory1 = new Inventory("apple",new BigDecimal(3),"1KG");
        Cart item2 = new Cart(inventory1,"apple", 3, "KG");
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
