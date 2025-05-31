package com.project.swadesi.controller;

import com.project.swadesi.entity.Product;
import com.project.swadesi.entity.Users;
import com.project.swadesi.service.ProductService;
import com.project.swadesi.service.UsersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {

    private final UsersService usersService;
    private final ProductService productService;

    @Autowired
    public UsersController(UsersService usersService,ProductService productService) {
        this.usersService = usersService;
        this.productService = productService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid Users users, Model model) {
        Optional<Users> optionalUsers = usersService.getUserByEmail(users.getEmail());
        if (optionalUsers.isPresent()) {
            model.addAttribute("error", "Email already registered,try to login or register with other email.");
            model.addAttribute("user", new Users());
            return "register";
        }
        usersService.addNew(users);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/";
    }
    
    @GetMapping("/product/{id}")
    public String product(@PathVariable("id") Long id, Model model) {
        // Fetch product from DB using the ID
        Product productDetails =  productService.productDetails(id);

        if (productDetails != null) {
            model.addAttribute("product", productDetails);
            System.out.println("Fetched Product: " + productDetails);
            System.out.println("name: " + productDetails.getName() + "price"+ productDetails.getPrice() +"url: " + productDetails.getUrl());
            return "productDetails"; 
        } else {
            return "redirect:/error"; 
        }
    }
    
    @GetMapping("/category/{name}")
    public String product(@PathVariable("name") String name, Model model) {
        // Fetch product from DB using the ID
        List<Product> products = productService.getCategoryProducts(name);
        //List<Product> productss = productRepository.findAll();
	    model.addAttribute("products", products);

        if (products != null) {
            model.addAttribute("product", products);
            System.out.println("Fetched Product: " + products);
            return "productsByCategory"; 
        } else {
            return "redirect:/error"; 
        }
    }
    
    @GetMapping("/refundPolicy")
    public String refundPolicy(Model model) {

        Object currentUserProfile = usersService.getCurrentUserProfile();
        model.addAttribute("user", currentUserProfile);

        return "refundPolicy";
    }
    
    
    @GetMapping("/dashboard")
    public String searchJobs(Model model) {

        Object currentUserProfile = usersService.getCurrentUserProfile();
        model.addAttribute("user", currentUserProfile);

        return "dashboard";
    }
}
