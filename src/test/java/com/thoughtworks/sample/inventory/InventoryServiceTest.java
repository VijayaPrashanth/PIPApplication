package com.thoughtworks.sample.inventory;

import com.thoughtworks.sample.exception.ItemNotFoundException;
import com.thoughtworks.sample.inventory.repository.Inventory;
import com.thoughtworks.sample.inventory.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryServiceTest {

    @InjectMocks
    private InventoryService inventoryService;

    @Mock
    private InventoryRepository inventoryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnItemList() {

        Inventory item1 = new Inventory("onion",new BigDecimal(40),"1KG");
        Inventory item2 = new Inventory("tomato",new BigDecimal(100),"1KG");
        List<Inventory> inventoryList = Arrays.asList(item1,item2);


        when(inventoryRepository.findAll()).thenReturn(inventoryList);

        List<Inventory> itemList = inventoryService.getItemList();

        assertEquals(inventoryList, itemList);
    }

    @Test
    public void shouldAddItemToInventory() {
        Inventory item = new Inventory("onion",new BigDecimal(40),"1KG");

        when(inventoryRepository.save(item)).thenReturn(item);
        inventoryService.addItems(item);

        verify(inventoryRepository,times(1)).save(item);
    }

    @Test
    public void shouldUpdateItemToInventory() throws ItemNotFoundException {
        Inventory item = new Inventory("onion",new BigDecimal(40),"1KG");
        int id = item.getId();
        List<Inventory> itemList = Arrays.asList(item);

        when(inventoryRepository.existsById(id)).thenReturn(true);
        when(inventoryService.updateItems(id,item)).thenReturn(itemList);

        assertEquals(itemList,inventoryService.updateItems(id,item));
        verify(inventoryRepository,times(2)).save(item);

    }

    @Test
    public void shouldThrowExceptionWhenInvalidIdIsGivenForUpdatingInventory() throws ItemNotFoundException {
        Inventory item = new Inventory("onion",new BigDecimal(40),"1KG");
        int id = item.getId();
        List<Inventory> itemList = Arrays.asList(item);

        when(inventoryRepository.existsById(id)).thenReturn(false);

        assertThrows(ItemNotFoundException.class,()->inventoryService.updateItems(id,item));
    }

    @Test
    public void shouldDeleteItemFromInventory() throws ItemNotFoundException {
        int id = 1;

        when(inventoryRepository.existsById(id)).thenReturn(true);
        String result = inventoryService.deleteItem(id);

        verify(inventoryRepository, times(1)).deleteById(id);
        assertEquals("Product removed from Inventory", result);
    }

    @Test
    public void shouldThrowExceptionWhenIdIsNotFoundForDeletion() {
        int id = 1;

        when(inventoryRepository.existsById(id)).thenReturn(false);
        assertThrows(ItemNotFoundException.class, () -> {
            inventoryService.deleteItem(id);
        });

        verify(inventoryRepository, never()).deleteById(id);
    }
}
