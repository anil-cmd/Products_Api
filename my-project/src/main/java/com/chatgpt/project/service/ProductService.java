package com.chatgpt.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chatgpt.project.entity.Product;


public interface ProductService {
	
	Product getProduct(Long id);
	
	List<Product> getAllProducts();
	
	Product createProduct(Product product);
	
	boolean updateProduct(Long id, Product product);
	
	boolean deleteProduct(Long id);

}
