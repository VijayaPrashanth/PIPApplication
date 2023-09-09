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

    public List<Cart> getItems() {
        return cartRepository.getItemDetails();
    }

    public List<Cart> addItems(Cart cart) throws ItemNotFoundException {
        cartRepository.save(cart);
        return cartRepository.findAll();
    }

    public List<Cart> addEditedItems(Cart cart) throws ItemNotFoundException {
        Inventory inventory = cart.getInventory();
        Cart itemFromCartByInventoryId = cartRepository.getItemFromCartByInventoryId(inventory.getId());
        if(itemFromCartByInventoryId==null){
            throw new ItemNotFoundException();
        }
        itemFromCartByInventoryId.setQuantity(cart.getQuantity());
        cartRepository.save(itemFromCartByInventoryId);
        return cartRepository.findAll();
    }

    public String deleteItem(int id) throws ItemNotFoundException {
        if(!cartRepository.existsById(id))
            throw new ItemNotFoundException();
        cartRepository.deleteById(id);
        return "Item removed from the cart";
    }

    public String deleteItemByInventoryId(int id) {
        if(!cartRepository.existsById(id))
        {
            return "This item is not present in cart";
        }
        cartRepository.deleteById(id);
        return "Item removed from cart as it is modified in inventory";
    }
}
