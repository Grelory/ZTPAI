package com.grelory.quickbill.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@Tag(name = "User View Controller", description = "APIs for user view operations")
public class UserViewController {

    public UserViewController() {
    }

    @GetMapping("/dashboard")
    @Operation(summary = "Get user dashboard", description = "Retrieve the user dashboard view")
    public String dashboard(@CookieValue("jwt_token") String token, Model model) {
        model.addAttribute("name", "User");
        return "user/dashboard";
    }
}
