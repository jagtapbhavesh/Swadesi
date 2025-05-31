package com.project.swadesi.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.swadesi.dto.CartProductView;
import com.project.swadesi.entity.CartItem;
import com.project.swadesi.entity.Product;
import com.project.swadesi.repository.CartItemRepository;
import com.project.swadesi.repository.ProductRepository;

@Service
public class CartService {
	

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;
    
    Logger log = LoggerFactory.getLogger(CartService.class);


    public List<CartProductView> addToCart(String userName, Long productId, Integer quantity, String size) {

        // 1. Find product by productId
        Product product = productRepository.findById(productId)
                            .orElseThrow(() -> new RuntimeException("Product not found!"));
        
        log.info("Product :: " + product);

        // 2. Check available stock based on selected size
        int availableStock = getStockBySize(product, size);

        if (availableStock < quantity) {
            throw new RuntimeException("Not enough stock for size " + size + "!");
        }

        // 3. Check if the item with same product + same size already exists for user
        CartItem existingItem = cartItemRepository.findByUserNameAndProductIdAndSize(userName, productId, size);

        if (existingItem != null) {
            // Update quantity if already exists
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            // Create new cart item
            CartItem newItem = new CartItem();
            newItem.setUserName(userName);
            newItem.setProductId(productId);
            newItem.setQuantity(quantity);
            newItem.setSize(size);
            cartItemRepository.save(newItem);
        }

        // 4. Fetch updated cart items for the user
        List<CartItem> cartItems = cartItemRepository.findByUserName(userName);

        List<CartProductView> cartProductViews = new ArrayList<>();

        for (CartItem item : cartItems) {
            Product itemProduct = productRepository.findById(item.getProductId())
                                    .orElseThrow(() -> new RuntimeException("Product not found for cart item!"));

            CartProductView view = new CartProductView();
            view.setProductId(itemProduct.getId());
            view.setProductName(itemProduct.getName());
            view.setPrice(itemProduct.getPrice());
            view.setQuantity(item.getQuantity());
            view.setSize(item.getSize());

            cartProductViews.add(view);
        }
        return cartProductViews;
    }
    
    private int getStockBySize(Product product, String size) {
        switch (size.toUpperCase()) {
            case "S":
                return product.getSmallCount();
            case "M":
                return product.getMediumCount();
            case "L":
                return product.getLargeCount();
            case "XL":
                return product.getExtraLargeCount();
            case "XXL":
                return product.getDoubleXLCount();
            default:
                throw new RuntimeException("Invalid size selected!");
        }
    }
}
