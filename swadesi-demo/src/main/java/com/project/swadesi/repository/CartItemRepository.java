package com.project.swadesi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.project.swadesi.entity.CartItem;

import jakarta.transaction.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>  {

	CartItem findByUserNameAndProductIdAndSize(String userName, Long productId, String size);

	List<CartItem> findByUserName(String userName);
	
	@Modifying
    @Transactional
    @Query("delete from CartItem ci where ci.productId = :productId and ci.size=:size and ci.userName =:userName")
    int deleteByProductId(@Param("productId") Long productId,@Param("size") String size, @Param("userName") String userName);

}
