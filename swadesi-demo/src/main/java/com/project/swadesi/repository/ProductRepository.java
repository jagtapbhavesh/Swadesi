package com.project.swadesi.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.swadesi.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	Product getById(Long id);

    @Query("SELECT p FROM Product p WHERE p.category IS NOT NULL AND p.category = :name")
	List<Product> getByCategory(String name);	
	
    @Query("SELECT p FROM Product p WHERE p.collection IS NOT NULL AND p.collection = :name")
	List<Product> getByCollection(String name);
}
