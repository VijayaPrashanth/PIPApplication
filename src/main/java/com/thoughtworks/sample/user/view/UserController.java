package com.thoughtworks.sample.user.view;

import com.thoughtworks.sample.user.UserPrincipal;
import com.thoughtworks.sample.user.UserPrincipalService;
import com.thoughtworks.sample.user.UserService;
import com.thoughtworks.sample.user.repository.User;
import com.thoughtworks.sample.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@RestController
public class UserController {
    private final UserService userService;

    private UserRepository userRepository;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    Map<String, Object> login(Principal principal) {
        String username = principal.getName();
        Map<String, Object> userDetails = new HashMap<>();

        userDetails.put("username", username);
        return userDetails;

    }

}

