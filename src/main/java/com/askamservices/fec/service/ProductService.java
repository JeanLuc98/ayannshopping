package com.askamservices.fec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.askamservices.fec.dao.ProductDAO;
import com.askamservices.fec.model.Product;

@Service
public class ProductService {
	
	@Autowired
	private ProductDAO productDAO;
	
	public List<Product> listProducts(){
		
		return productDAO.findAll();
		
	}
	
	public Product getProduct(final Integer productId){
		
		return productDAO.findById(productId).get();
	}

	
	public void deleteProduct(Integer productId) {
		
		productDAO.deleteById(productId);
	}
	
	public Product saveProduct(Product product) {
		
		return productDAO.save(product);
	}
	
}
