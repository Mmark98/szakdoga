package com.example.cultureplacesfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.cultureplacesfinder.service.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) boolean error, Model model) {
        if (error) {
            model.addAttribute("loginError", "Invalid username or password.");
        }
        return "auth/login";
    }

   

    @PostMapping("/login")
    public String handleLogin(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        // Felhasználó hitelesítése
        boolean authenticated = userService.authenticateUser(email, password);

        // Sikeres bejelentkezés esetén átirányítás
        if (authenticated) {
            return "redirect:/heritages"; // Heritages oldalra történő átirányítás
        } else {
            // Sikertelen bejelentkezés esetén hibaüzenet megjelenítése
            model.addAttribute("error", "Hibás felhasználónév vagy jelszó.");
            return "login"; // A login oldal újbóli megjelenítése
        }

    }
 }

