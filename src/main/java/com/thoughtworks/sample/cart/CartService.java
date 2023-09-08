package com.thoughtworks.sample.cart;

import com.thoughtworks.sample.cart.repository.Cart;
import com.thoughtworks.sample.cart.repository.CartRepository;
import com.thoughtworks.sample.exception.ItemNotFoundException;
import com.thoughtworks.sample.inventory.repository.Inventory;
import com.thoughtworks.sample.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> addItems(Cart cart) throws ItemNotFoundException {

        if(!inventoryRepository.existsById(cart.getId()))
            throw new ItemNotFoundException();
        Optional<Inventory> inventoryOptional = inventoryRepository.findById(cart.getId());
        Inventory inventory = inventoryOptional.get();
        Cart cart1 = new Cart(inventory, cart.getName(), cart.getQuantity(), cart.getUnit());

        cartRepository.save(cart1);
        return cartRepository.getItemDetails();
    }


    public List<Cart> getItems() {

        return cartRepository.getItemDetails();
    }

    public String deleteItem(int id) throws ItemNotFoundException {
        if(!cartRepository.existsById(id))
            throw new ItemNotFoundException();
        cartRepository.deleteById(id);
        return "Item removed from the cart";
    }
}
