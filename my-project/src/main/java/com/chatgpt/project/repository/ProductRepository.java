package com.chatgpt.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatgpt.project.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
