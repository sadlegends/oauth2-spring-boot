package com.marcusdacoregio.authservice.controller;

import com.marcusdacoregio.authservice.domain.User;
import com.marcusdacoregio.authservice.dto.UserRegistrationDto;
import com.marcusdacoregio.authservice.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/current")
    public Principal getUser(Principal principal) {
        return principal;
    }

    @PostMapping
    @PreAuthorize("#oauth2.hasScope('server')")
    public User createUser(@Valid @RequestBody UserRegistrationDto userRegistration) {
        User savedUser = userService.create(toUser(userRegistration));
        return savedUser; // TODO create a DTO for user
    }

    private User toUser(UserRegistrationDto userRegistration) {
        User user = new User();
        user.setUsername(userRegistration.getUsername());
        user.setPassword(userRegistration.getPassword());
        return user;
    }

}