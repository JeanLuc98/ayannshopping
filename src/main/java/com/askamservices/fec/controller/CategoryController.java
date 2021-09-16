package com.askamservices.fec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.askamservices.fec.model.Category;
import com.askamservices.fec.model.CustomUserDetail;
import com.askamservices.fec.model.User;
import com.askamservices.fec.service.CategoryService;
import com.askamservices.fec.service.UserService;

@Controller
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	@Autowired
	UserService userService;
	
//	@GetMapping("/categories")
//	public String showAllCategories(Model model) {
//		
//		List<Category> categories = categoryService.listCategories();
//		
//		model.addAttribute("categories", categories);
//		
//		return "category";
//	}
	
	@GetMapping("/category")
	public String showCategory(Model model) {
		
		List<Category> categories = categoryService.listCategories();
		CustomUserDetail connectedUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User userId = userService.getUser(connectedUser.getUserId());
		model.addAttribute("userConnectedName", userId.getUsername());
		
		Category category = new Category();

		model.addAttribute("categories", categories);
		model.addAttribute("category", category);
		
		return "category";
	}
	
	/**
	 * This method permit to edit an existing category
	 * 
	 * */
	
	@GetMapping("/editCategory/{categoryId}")
	public String updateCategory(@PathVariable("categoryId") final Integer categoryId, Model model) {
		
		CustomUserDetail connectedUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User userId = userService.getUser(connectedUser.getUserId());
		model.addAttribute("userConnectedName", userId.getUsername());
		
		Category cat = categoryService.getCategory(categoryId);
		
		model.addAttribute("category", cat);	
		
		return "formUpdateCategory";		
	}
	
	/**
	 * Update
	 * This method permit to update the edited category
	 * 
	 * */
	
	@PutMapping("/updateCategory")
	public Category categoryToUpdate(@RequestBody Category category) {
	
		Category cat = categoryService.getCategory(category.getCategoryId());
	
	if (cat != null) {
		
		Category currentCategory = cat;
		
		String categoryName = category.getCategoryName();
		if (categoryName != null) {
			currentCategory.setCategoryName(categoryName);
		}
		
		String categoryDesc = category.getCategoryDescription();
		if (categoryDesc != null) {
			currentCategory.setCategoryDescription(categoryDesc);
		}
		
		categoryService.saveCategory(currentCategory);
		
		return currentCategory;
		
	} else {
		return null;
	}
}
	
	@PostMapping("/addCategory")
	public ModelAndView addCategory(@ModelAttribute Category category) {
		
		if (category.getCategoryId() != null) {
			
			categoryToUpdate(category);
			
		} else {
			
			categoryService.saveCategory(category);
		}
			
		return new ModelAndView("redirect:/category");
	}
			
	@GetMapping("/delete/{id}")
	public ModelAndView showAllCategories(@PathVariable("id") Integer categoryId, Model model) {
		
		categoryService.deleteCategory(categoryId);
		
		return new ModelAndView("redirect:/category");
	
	}

}
