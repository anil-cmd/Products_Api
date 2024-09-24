package com.chatgpt.project.controller;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chatgpt.project.entity.Product;
import com.chatgpt.project.service.ProductService;

@RestController
public class ProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/product/{id}")
	public ResponseEntity<Object> fetchProduct(@PathVariable("id") Long id) {
		
		if(id == null || id ==0) {
			logger.error("id cannot be null and cannot be zero");
			return new ResponseEntity<Object>("provided id is invalid", HttpStatus.BAD_REQUEST);
		}
		Product product = productService.getProduct(id);
		try {
			if(product != null) {
				return new ResponseEntity<Object>(product, HttpStatus.OK);
			}else {
	            logger.error("Product not found for the given ID");
	            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
	        }
		}catch (Exception e) {
			logger.error("something went wrong");
			return new ResponseEntity<Object>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/products")
	public ResponseEntity<Object> fetchAllProducts() {
		
		try {
			List<Product> allProducts = productService.getAllProducts();
			if(!allProducts.isEmpty()) {
				return new ResponseEntity<Object>(allProducts, HttpStatus.OK);
			}else {
				logger.error("products are not available in the database");
				return new ResponseEntity<Object>("products are not available in the database", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("something went wrong");
			return new ResponseEntity<Object>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/product")
	public ResponseEntity<Object> insertProduct(@RequestBody Product product) {
		
		try {
			Objects.requireNonNull(product, "product must not be null");
			Product savedProduct = productService.createProduct(product);
			return new ResponseEntity<Object>(savedProduct, HttpStatus.OK);
			
		} catch (IllegalArgumentException e) {
			logger.error("product input type is wrong");
			return new ResponseEntity<Object>("products are not available in the database", HttpStatus.BAD_REQUEST);
		}catch (NullPointerException e) {
			logger.error("product must not be null");
			return new ResponseEntity<Object>("product is not given", HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			logger.error("something went wrong");
			return new ResponseEntity<Object>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<Object> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
		
		if(id == null || id == 0) {
			logger.error("id is either null or zero");
			return new ResponseEntity<Object>("provided id is invalid", HttpStatus.BAD_REQUEST);
		}
		
		Objects.requireNonNull(product, "product must not be null");
		
		try {
			
			boolean updateProduct = productService.updateProduct(id, product);
			if(updateProduct) {
				logger.info("product updated successfully");
				return new ResponseEntity<Object>("product updated successfully", HttpStatus.CREATED);
			}else {
				logger.error("product is not updated somehow");
				return new ResponseEntity<Object>("product is not updated somehow", HttpStatus.INTERNAL_SERVER_ERROR);
			}	
		} catch (IllegalArgumentException e) {
			logger.error("product input type is wrong");
			return new ResponseEntity<Object>("products are not available in the database", HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			logger.error("something went wrong");
			return new ResponseEntity<Object>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id) {
		
		if(id == null || id == 0) {
			logger.error("id is either null or zero");
			return new ResponseEntity<Object>("provided id is invalid", HttpStatus.BAD_REQUEST);
		}
		
		try {
			boolean deleteProduct = productService.deleteProduct(id);
			if(deleteProduct) {
				logger.info("product deleted successfully");
				return new ResponseEntity<Object>("product deleted successfully", HttpStatus.OK);
			}else {
				logger.error("product is not deleted somehow");
				return new ResponseEntity<Object>("product is not deleted somehow", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.error("something went wrong");
			return new ResponseEntity<Object>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}