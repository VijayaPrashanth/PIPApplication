package com.thoughtworks.sample.inventory.view;

import com.thoughtworks.sample.inventory.InventoryService;
import com.thoughtworks.sample.inventory.repository.Inventory;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@WithMockUser
public class InventoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private InventoryController inventoryController;
    @MockBean
    private InventoryService inventoryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnItemsFromInventory() throws Exception {
        // Mock the behavior of the InventoryService
        Inventory item1 = new Inventory("Item1", new BigDecimal(10));
        item1.setId(1);
        Inventory item2 = new Inventory("Item2", new BigDecimal(5));
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
        // Perform a GET request to the /inventory endpoint
        mockMvc.perform(get("/inventory"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().json(priceListJson));
    }
}
