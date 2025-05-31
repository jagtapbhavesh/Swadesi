package com.project.swadesi.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.swadesi.dto.CartItemDTO;
import com.project.swadesi.dto.CartProductView;
import com.project.swadesi.entity.Product;
import com.project.swadesi.repository.CartItemRepository;
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
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
    Logger log = LoggerFactory.getLogger(CollectionController.class);

	
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

	    String userName = (String) session.getAttribute("USER_ID");
	    log.info("userId ::"+userName);
	    Product product = productRepository.findById(productId)
	                        .orElseThrow(() -> new RuntimeException("Product not found!"));

	    int availableStock = getStockBySize(product, size);

	    if (availableStock < quantity) {
	        model.addAttribute("errorMessage", "Not enough stock for size " + size + "!");
	        return "cart"; 
	    }

	    if (userName == null) {
	        // Guest user
	        List<CartProductView> guestCart = (List<CartProductView>) session.getAttribute("GUEST_CART");

	        if (guestCart == null) {
	            guestCart = new ArrayList<>();
	        }

	        boolean found = false;
	        for (CartProductView item : guestCart) {
	            if (item.getProductId().equals(productId) && item.getSize().equalsIgnoreCase(size)) {
	                item.setQuantity(item.getQuantity() + quantity);
	                found = true;
	                break;
	            }
	        }
	        if (!found) {
	            CartProductView productView = new CartProductView();
	            productView.setProductId(productId);
	            productView.setProductName(product.getName());
	            productView.setPrice(product.getPrice());
	            productView.setQuantity(quantity);
	            productView.setSize(size);
	            guestCart.add(productView);
	        }

	        session.setAttribute("GUEST_CART", guestCart);
	        model.addAttribute("cartProducts", guestCart);
	        model.addAttribute("successMessage", "Product added to guest cart!");

	    } else {
	        // Logged-in user
	    	log.info("Logged-in user :: ");
	        List<CartProductView> userCart = cartService.addToCart(userName, productId, quantity, size);
	        model.addAttribute("cartProducts", userCart);
	        model.addAttribute("successMessage", "Product added to user cart!");
	    }

	    return "cart";
	}

	@PostMapping("/addToCart/remove")
    public String removeFromCart(@RequestParam("productId") Long productId,
    		@RequestParam("size") String size, @RequestParam("userName") String userName,
                                 RedirectAttributes redirectAttributes) {
        int removed = cartItemRepository.deleteByProductId(productId,size,userName);
        log.info("Product exist in table ::"+removed);
        if (removed > 0) {
        	log.info("Product removed");
            redirectAttributes.addFlashAttribute("successMessage", "Product removed from cart.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to remove product from cart.");
        }

        return "redirect:/cart";
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
