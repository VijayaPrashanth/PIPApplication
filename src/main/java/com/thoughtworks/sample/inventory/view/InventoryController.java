package com.thoughtworks.sample.inventory.view;

import com.thoughtworks.sample.inventory.InventoryService;
import com.thoughtworks.sample.inventory.repository.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
