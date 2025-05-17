package com.project.swadesi.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.swadesi.entity.Product;
import com.project.swadesi.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productDao;

	public Product productDetails(Long id) {
		return productDao.getById(id);
	}

	public List<Product> getCategoryProducts(String name) {
		// TODO Auto-generated method stub
		return productDao.getByCategory(name);
	}
	
	public List<Product> getCollectionProducts(String name) {
		return productDao.getByCollection(name);
	}

}
