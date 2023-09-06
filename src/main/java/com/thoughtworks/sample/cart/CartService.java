package com.thoughtworks.sample.cart;

import com.thoughtworks.sample.cart.repository.Cart;
import com.thoughtworks.sample.cart.repository.CartRepository;
import com.thoughtworks.sample.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> addItems(Cart cart) {

        cartRepository.save(cart);
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
