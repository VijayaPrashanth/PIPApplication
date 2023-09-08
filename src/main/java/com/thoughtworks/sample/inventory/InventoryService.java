package com.thoughtworks.sample.inventory;

import com.thoughtworks.sample.exception.ItemNotFoundException;
import com.thoughtworks.sample.inventory.repository.Inventory;
import com.thoughtworks.sample.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<Inventory> getItemList() {
        return inventoryRepository.findAll();
    }

    public List<Inventory> addItems(Inventory inventory) {
        inventoryRepository.save(inventory);
        return inventoryRepository.getItems();
    }
    public String deleteItem(int id) throws ItemNotFoundException {
        if(!inventoryRepository.existsById(id))
            throw new ItemNotFoundException();
        inventoryRepository.deleteById(id);
        return "Product removed from Inventory";
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
