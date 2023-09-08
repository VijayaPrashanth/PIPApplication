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
import java.util.ArrayList;
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
        List<Inventory> inventoryList = new ArrayList<>();
        Inventory item = new Inventory("onion",new BigDecimal(40),"1KG");
        inventoryList.add(item);

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
