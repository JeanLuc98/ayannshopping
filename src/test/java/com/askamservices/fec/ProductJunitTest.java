package com.askamservices.fec;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.askamservices.fec.model.Product;
import com.askamservices.fec.service.ProductService;

@SpringBootTest
class ProductJunitTest {
	
	@Autowired
	private ProductService productService;

	@Test
	public void addProductTest() {
		
		Product product = new Product();
		
		product.setProductName("Shirt");
		product.setProductDescription("United Colors of Cotton");
		product.setPrice(200);
		product.setStock(35);
		product.setCategoryId("T-shirt");
		product.setSupplierId(15);
		
		productService.saveProduct(product);
	}

}
