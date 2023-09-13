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
    @GetMapping()
    public List<Cart> getItems() {
        return cartService.getItems();
    }

    @PutMapping()
    public List<Cart> putItem(@RequestBody Cart cart) throws ItemNotFoundException {
        return cartService.addItems(cart);
    }

    @PutMapping("/{id}")
    public List<Cart> updateItemsCount(@PathVariable int id,@RequestBody int itemsCount) throws ItemNotFoundException {
        return cartService.updateItemsCount(id,itemsCount);
    }

    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable int id) throws ItemNotFoundException {
        return cartService.deleteItem(id);
    }
}
