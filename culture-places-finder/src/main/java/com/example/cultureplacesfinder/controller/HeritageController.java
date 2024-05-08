package com.example.cultureplacesfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.cultureplacesfinder.model.Heritage;
import com.example.cultureplacesfinder.service.HeritageService;

import java.util.List;

@Controller
public class HeritageController {

	 @Autowired
	    private HeritageService heritageService;
	 
	 @GetMapping("/heritages")
	    public String showHeritagesForm() {
	        return "heritages";  // Győződj meg róla, hogy a 'login.html' nézet létezik a sablonok könyvtárában
	    }
	

	 @GetMapping("/upload")
	 public String showUploadForm(Model model) {
	     model.addAttribute("heritages", new Heritage());
	     return "upload";
	 }


	    
	    @GetMapping("/map")
	    public String showMap() {
	        return "map"; // returns map.html
	    }
	 @GetMapping("/api/heritages")
	    public String searchHeritages(@RequestParam(required = false) String searchName, Model model) {
	        List<Heritage> heritages = heritageService.findHeritagesByName(searchName);
	        model.addAttribute("heritages", heritages);
	        return "heritages";
	    }
	
	 
	 @PostMapping("/upload")
	 public String processUpload(@ModelAttribute("heritages") Heritage heritage, BindingResult result, Model model) {
	     if (result.hasErrors()) {
	         return "upload";
	     }
	     // az 'heritage' adatainak feldolgozása
	     return "redirect:/success";
	 }

	 
	 
	 @GetMapping("/search")
	    public List<Heritage> findHeritagesByName(@RequestParam String name) {
	        return heritageService.findHeritagesByName(name);
	    }
}
