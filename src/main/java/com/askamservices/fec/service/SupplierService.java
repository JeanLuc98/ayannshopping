package com.askamservices.fec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.askamservices.fec.dao.SupplierDAO;
import com.askamservices.fec.model.Supplier;

@Service
public class SupplierService {

	@Autowired
	private SupplierDAO supplierDAO;
	
	public List<Supplier> listSuppliers(){
		
		return supplierDAO.findAll();
		
	}
	
	public Supplier getSupplier(final Integer supplierId){
		
		return supplierDAO.findById(supplierId).get();
	}

	
	public void deleteSupplier(final Integer supplierId) {
		
		supplierDAO.deleteById(supplierId);
	}
	
	public Supplier saveSupplier(Supplier supplier) {
		
		return supplierDAO.save(supplier);
	}
	
	
	
}
