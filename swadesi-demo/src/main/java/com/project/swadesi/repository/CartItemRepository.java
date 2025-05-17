package com.project.swadesi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.swadesi.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>  {

	CartItem findByUserIdAndProductIdAndSize(Long userId, Long productId, String size);

}
