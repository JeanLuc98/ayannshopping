package com.askamservices.fec.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.askamservices.fec.model.Category;

@Repository
public interface CategoryDAO extends JpaRepository<Category, Integer>{

//	public boolean addCategory(Category category);
//	public boolean deleteCategory(Category category);
//	public boolean updateCategory(Category category);
//	
//	public List<Category> listCategories();
//	public Category getCategory(int categoryId);
}
