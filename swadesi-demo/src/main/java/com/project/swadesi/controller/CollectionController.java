package com.project.swadesi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.swadesi.dto.CartItemDTO;
import com.project.swadesi.dto.CartProductView;
import com.project.swadesi.entity.Product;
import com.project.swadesi.repository.ProductRepository;
import com.project.swadesi.service.CartService;
import com.project.swadesi.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CollectionController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartService cartService;
	
	@GetMapping("/collection/{name}")
    public String product(@PathVariable("name") String name, Model model) {
        List<Product> products = productService.getCollectionProducts(name);
	    model.addAttribute("products", products);

        if (products != null) {
            model.addAttribute("product", products);
            System.out.println("Fetched Product: " + products);
            return "productsByCategory"; 
        } else {
            return "redirect:/error"; 
        }
    }
	
	@GetMapping("/addToCart")
	public String addToCart(@RequestParam Long productId,
	                        @RequestParam Integer quantity,
	                        @RequestParam String size,
	                        HttpSession session,
	                        Model model) {

	    Long userId = (Long) session.getAttribute("USER_ID");

	    // Always validate stock first
	    Product product = productRepository.findById(productId)
	                        .orElseThrow(() -> new RuntimeException("Product not found!"));

	    int availableStock = getStockBySize(product, size);

	    if (availableStock < quantity) {
	        model.addAttribute("errorMessage", "Not enough stock for size " + size + "!");
	        return "cart"; // your cart.html page (or error page)
	    }

	    if (userId == null) {
	        // Guest user - save in session
	        List<CartItemDTO> guestCart = (List<CartItemDTO>) session.getAttribute("GUEST_CART");

	        if (guestCart == null) {
	            guestCart = new ArrayList<>();
	        }

	        boolean found = false;
	        for (CartItemDTO item : guestCart) {
	            if (item.getProductId().equals(productId) && item.getSize().equalsIgnoreCase(size)) {
	                item.setQuantity(item.getQuantity() + quantity);
	                found = true;
	                break;
	            }
	        }
	        if (!found) {
	            guestCart.add(new CartItemDTO(productId, quantity, size));
	        }

	        session.setAttribute("GUEST_CART", guestCart);
	        model.addAttribute(size, guestCart);
	        model.addAttribute("successMessage", "Product added to guest cart!");
	    } else {
	        // Logged-in user - save in database
	    	cartService.addToCart(userId, productId, quantity, size);
	    	model.addAttribute("successMessage", "Product added to user cart!");
	    }

	    return "cart"; // returns to cart.html page
	}


    private int getStockBySize(Product product, String size) {
        switch (size.toUpperCase()) {
            case "S": return product.getSmallCount();
            case "M": return product.getMediumCount();
            case "L": return product.getLargeCount();
            case "XL": return product.getExtraLargeCount();
            case "XXL": return product.getDoubleXLCount();
            default: throw new RuntimeException("Invalid size selected!");
        }
    }
		
}
