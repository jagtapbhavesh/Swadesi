package com.project.swadesi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public void addToCart(Long userId, Long productId, Integer quantity, String size) {
		
		 // 1. Find product by productId
        Product product = productRepository.findById(productId)
                            .orElseThrow(() -> new RuntimeException("Product not found!"));

        // 2. Check available stock based on selected size
        int availableStock = getStockBySize(product, size);

        if (availableStock < quantity) {
            throw new RuntimeException("Not enough stock for size " + size + "!");
        }

        // 3. Check if the item with same product + same size already exists for user
        CartItem existingItem = cartItemRepository.findByUserIdAndProductIdAndSize(userId, productId, size);

        if (existingItem != null) {
            // Update quantity if already exists
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            // Create new cart item
            CartItem newItem = new CartItem();
            newItem.setUserId(userId);
            newItem.setProductId(productId);
            newItem.setQuantity(quantity);
            newItem.setSize(size);
            cartItemRepository.save(newItem);
        }
    }

    // Helper method to get stock based on size
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
