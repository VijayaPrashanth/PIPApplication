package com.thoughtworks.sample.inventory;

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
        Inventory item = new Inventory("onion",new BigDecimal(40));
        inventoryList.add(item);

        when(inventoryRepository.findAll()).thenReturn(inventoryList);

        List<Inventory> itemList = inventoryService.getItemList();

        assertEquals(inventoryList, itemList);
    }
}
