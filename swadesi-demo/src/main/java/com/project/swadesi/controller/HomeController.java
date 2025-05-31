package com.project.swadesi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.swadesi.entity.Product;
import com.project.swadesi.repository.ProductRepository;
import com.project.swadesi.service.UsersService;

@Controller
public class HomeController {
	
	@Autowired
    private ProductRepository productRepository;
	
	@Autowired
	private UsersService usersService;


	@GetMapping("/")
	public String home(Model model) {
	    List<Product> products = productRepository.findAll();
	    Object currentUserProfile = usersService.getCurrentUserProfile();

	    model.addAttribute("products", products);
        model.addAttribute("user", currentUserProfile);
	    return "index";
	}
}
