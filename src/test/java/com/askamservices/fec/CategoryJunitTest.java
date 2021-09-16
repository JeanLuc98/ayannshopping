package com.askamservices.fec;

import java.util.List;

//import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.askamservices.fec.model.Category;
import com.askamservices.fec.service.CategoryService;

@SpringBootTest
class CategoryJunitTest {
	
	@Autowired
	private CategoryService categoryService;

	@Test
	public void addCategoryTest(){
		
		Category category = new Category();
		
		category.setCategoryName("Shirtssss");
		category.setCategoryDescription("All variety of Shirts");
		
		categoryService.saveCategory(category);
		
		
	}
	
	@Test
	public void listCategoryTest() {
		
		List<Category> listCategories = categoryService.listCategories();
		
		for (Category category : listCategories) {
			
			 System.out.println(category);
		}
		
	}
	
	
	@Test
	public void deleteCategoryTest() {
		
		categoryService.deleteCategory(4);
	}
	

}
