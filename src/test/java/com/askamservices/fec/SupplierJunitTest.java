package com.askamservices.fec;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.askamservices.fec.model.Supplier;
import com.askamservices.fec.service.SupplierService;

@SpringBootTest
class SupplierJunitTest {
	
	@Autowired
	private SupplierService supplierService;

	@Test
	public void addSupplierTest() {
		
		Supplier supplier = new Supplier();
		
		supplier.setAddress("Cocody, Abidjan");
		supplier.setSupplierName("Jean-Luc");
		
		supplierService.saveSupplier(supplier);
	}

}
