package com.askamservices.fec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.askamservices.fec.dao.CategoryDAO;
import com.askamservices.fec.model.Category;

@Service
public class CategoryService {

	@Autowired
	private CategoryDAO categoryDAO;
	
	public List<Category> listCategories(){
		
		return categoryDAO.findAll();
		
	}
	
	public Category getCategory(final Integer categoryId){
		
		return categoryDAO.findById(categoryId).get();
	}
	
	
	public void deleteCategory(Integer categoryId) {
		
		categoryDAO.deleteById(categoryId);
	}
	
	public Category saveCategory(Category category) {
		
		return categoryDAO.save(category);
	}
}
