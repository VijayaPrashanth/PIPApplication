package com.thoughtworks.sample.cart.view;

import com.thoughtworks.sample.cart.CartService;
import com.thoughtworks.sample.cart.repository.Cart;
import com.thoughtworks.sample.handlers.ErrorResponse;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetched admin details successfully"),
            @ApiResponse(code = 404, message = "Record not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Something failed in the server", response = ErrorResponse.class)
    })
    @PostMapping("/add")
    public Cart addItemsToCart(@Validated@RequestBody Cart cart)  {
        return cartService.addItems(cart);
    }
}
