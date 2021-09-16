package com.askamservices.fec.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.askamservices.fec.model.Product;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer>{

	
}
