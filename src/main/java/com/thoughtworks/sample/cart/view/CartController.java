package com.thoughtworks.sample.cart.view;

import com.thoughtworks.sample.cart.CartService;
import com.thoughtworks.sample.cart.repository.Cart;
import com.thoughtworks.sample.exception.ItemNotFoundException;
import com.thoughtworks.sample.handlers.ErrorResponse;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetched item details successfully"),
            @ApiResponse(code = 404, message = "Record not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Something failed in the server", response = ErrorResponse.class)
    })
    @PutMapping("/add")
    public List<Cart> putItems(@RequestBody Cart cart) throws ItemNotFoundException {
        return cartService.addItems(cart);
    }

    @PutMapping("/edit")
    public List<Cart> addEditedItemsToCart(@RequestBody Cart cart) throws ItemNotFoundException {
        return cartService.addEditedItems(cart);
    }

    @GetMapping()
    public List<Cart> viewItems() {
        return cartService.getItems();
    }

    @DeleteMapping("/{id}")
    public String deleteItemFromCart(@PathVariable int id) throws ItemNotFoundException {
        return cartService.deleteItem(id);
    }
    @DeleteMapping("/byInventory/{id}")
    public String deleteItemFromCartByInventoryId(@PathVariable int id) throws ItemNotFoundException {
        return cartService.deleteItemByInventoryId(id);
    }
}
