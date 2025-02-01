package com.grelory.quickbill.controllers;

import com.grelory.quickbill.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
@Tag(name = "Auth Controller", description = "APIs for authentication operations")
public class AuthController {

    private final AuthService authService;

    public AuthController(@Autowired AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    @Operation(summary = "Get login page", description = "Retrieve the login page")
    public String login() {
        return "auth/login";
    }

    @PostMapping("/login")
    @Operation(summary = "Post login credentials", description = "Verify credentials and return JWT token")
    public RedirectView postLogin(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpServletResponse response) {
        Optional<String> optToken = authService.verifyAndGenerateJwt(email, password);
        if (optToken.isPresent()) {
            Cookie cookie = new Cookie("jwt_token", optToken.get());
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(30 * 60 * 60);
            response.addCookie(cookie);
            return new RedirectView("/auth/redirect/dashboard");
        }
        return new RedirectView("/unauthorized");
    }

    @GetMapping("/logout")
    @Operation(summary = "Logout", description = "Invalidate the JWT token and logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt_token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "auth/logout";
    }

    @GetMapping("/registration")
    @Operation(summary = "Get registration page", description = "Retrieve the registration page")
    public String registration() {
        return "auth/registration";
    }

    @PostMapping("/registration")
    @Operation(summary = "Post registration details", description = "Register a new user")
    public RedirectView postRegistration(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("userName") String userName) {
        authService.saveUser(email, password, userName);
        return new RedirectView("/auth/login");
    }

    @GetMapping("/redirect/dashboard")
    @Operation(summary = "Redirect to dashboard", description = "Redirect to the appropriate dashboard based on user role")
    public RedirectView redirectDashboard(@CookieValue("jwt_token") String token) {
        return authService.findUserByToken(token).map(user -> {
            if ("ADMIN".equals(user.getRole())) return new RedirectView("/admin/dashboard");
            if ("USER".equals(user.getRole())) return new RedirectView("/user/dashboard");
            return null;
        }).orElse(new RedirectView("/auth/login"));
    }

    @GetMapping("/access-denied")
    @Operation(summary = "Access denied page", description = "Display access denied message")
    public String accessDenied(Model model) {
        model.addAttribute("message", "Sorry, you have no access to see this page.");
        model.addAttribute("path", "main");
        return "message";
    }

    @GetMapping("/unauthorized")
    @Operation(summary = "Unauthorized page", description = "Display unauthorized message")
    public String unauthorized(Model model) {
        model.addAttribute("message", "Sorry, you are not allowed to see this page. Please log in.");
        model.addAttribute("path", "login");
        return "message";
    }

}