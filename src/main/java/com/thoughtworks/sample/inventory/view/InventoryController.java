package com.thoughtworks.sample.inventory.view;

import com.thoughtworks.sample.exception.ItemNotFoundException;
import com.thoughtworks.sample.handlers.ErrorResponse;
import com.thoughtworks.sample.inventory.InventoryService;
import com.thoughtworks.sample.inventory.repository.Inventory;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping()
    public List<Inventory> getItems() {
        return inventoryService.getItemList();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetched admin details successfully"),
            @ApiResponse(code = 404, message = "Record not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Something failed in the server", response = ErrorResponse.class)
    })
    @PutMapping("/add")
    public List<Inventory> addItemsToInventory(@RequestBody Inventory inventory)  {
        return inventoryService.addItems(inventory);
    }
    @DeleteMapping("/{id}")
    public String deleteItemFromCart(@PathVariable int id) throws ItemNotFoundException {
        return inventoryService.deleteItem(id);
    }
}
