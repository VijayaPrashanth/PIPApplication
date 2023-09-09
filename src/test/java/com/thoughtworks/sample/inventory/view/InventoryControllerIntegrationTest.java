package com.thoughtworks.sample.inventory.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.sample.SampleApplication;
import com.thoughtworks.sample.cart.CartService;
import com.thoughtworks.sample.exception.ItemNotFoundException;
import com.thoughtworks.sample.inventory.InventoryService;
import com.thoughtworks.sample.inventory.repository.Inventory;
import com.thoughtworks.sample.inventory.repository.InventoryRepository;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = SampleApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@WithMockUser
public class InventoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Mock
    private InventoryRepository inventoryRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnItemsFromInventory() throws Exception {
        Inventory item1 = new Inventory("Item1", new BigDecimal(10),"KG");
        item1.setId(1);
        Inventory item2 = new Inventory("Item2", new BigDecimal(5),"KG");
        item2.setId(2);
        List<Inventory> itemList = Arrays.asList(item1, item2);
        when(inventoryService.getItemList()).thenReturn(itemList);

        String priceListJson = "[{"
                + "\"id\":1,"
                + "\"name\":\"Item1\","
                + "\"price\":10"
                + "},"
                +"{"
                + "\"id\":2,"
                + "\"name\":\"Item2\","
                + "\"price\":5"
                + "}"
                +"]";
        mockMvc.perform(get("/inventory"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().json(priceListJson));
    }


    @Test
    public void shouldAddItemsToInventory() throws Exception {
        Inventory item = new Inventory("onion", new BigDecimal("20.00"), "1KG");
        List<Inventory> itemList = new ArrayList<>();
        itemList.add(item);
        String response = new ObjectMapper().writeValueAsString(item);


        when(inventoryService.addItems(any(Inventory.class))).thenReturn(itemList);

        mockMvc.perform(MockMvcRequestBuilders.put("/inventory/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(response))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAddEditedItemsToInventory() throws Exception {
        Inventory item = new Inventory("onion", new BigDecimal("20.00"), "1KG");
        List<Inventory> itemList = new ArrayList<>();
        itemList.add(item);
        String response = new ObjectMapper().writeValueAsString(item);


        when(inventoryService.addItems(any(Inventory.class))).thenReturn(itemList);

        mockMvc.perform(MockMvcRequestBuilders.put("/inventory/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(response))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteProductsFromInventory() throws Exception, ItemNotFoundException {
        int id = 1;

        when(inventoryService.deleteItem(id)).thenReturn("Product removed from Inventory");

        mockMvc.perform(delete("/inventory/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Product removed from Inventory"));


        verify(inventoryService, times(1)).deleteItem(id);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidIdIsGiven() throws Exception, ItemNotFoundException {
        int id =4;
        when(inventoryRepository.existsById(id)).thenReturn(false);
        when(inventoryService.deleteItem(id)).thenThrow(ItemNotFoundException.class);

        mockMvc.perform(delete("/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }
}
