package com.thoughtworks.sample.inventory.view;

import com.thoughtworks.sample.exception.ItemNotFoundException;
import com.thoughtworks.sample.handlers.ErrorResponse;
import com.thoughtworks.sample.inventory.InventoryService;
import com.thoughtworks.sample.inventory.repository.Inventory;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetched admin details successfully"),
            @ApiResponse(code = 404, message = "Record not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Something failed in the server", response = ErrorResponse.class)
    })
    @GetMapping()
    public List<Inventory> getItem() {
        return inventoryService.getItemList();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Inventory> addItem(@RequestBody Inventory inventory)  {
        return inventoryService.addItems(inventory);
    }

    @PutMapping("/{id}")
    public List<Inventory> updateItem(@PathVariable int id,@RequestBody Inventory inventory) throws ItemNotFoundException {
        return inventoryService.updateItems(id,inventory);
    }
    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable int id) throws ItemNotFoundException {
        return inventoryService.deleteItem(id);
    }
}
