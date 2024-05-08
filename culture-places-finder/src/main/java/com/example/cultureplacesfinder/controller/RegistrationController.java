package com.example.cultureplacesfinder.controller;
 

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.cultureplacesfinder.dto.UserDto;
import com.example.cultureplacesfinder.model.User;
import com.example.cultureplacesfinder.service.UserService;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public ModelAndView registrationForm() {
        ModelAndView modelAndView = new ModelAndView("registration");
        modelAndView.addObject("userDto", new UserDto());
        return modelAndView;
    }
    
    @PostMapping("/registration")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        user.setId(UUID.randomUUID()); // Generálj egyedi azonosítót
        userService.registerUser(user);
        redirectAttributes.addFlashAttribute("successMessage", "Felhasználó sikeresen regisztrálva azonosítóval: " + user.getId());
        return "redirect:auth/login"; // Átirányítás a bejelentkezési oldalra
    }

}  



