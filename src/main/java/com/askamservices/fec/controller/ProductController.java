package com.askamservices.fec.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.askamservices.fec.model.Category;
import com.askamservices.fec.model.CustomUserDetail;
import com.askamservices.fec.model.Product;
import com.askamservices.fec.model.User;
import com.askamservices.fec.service.CategoryService;
import com.askamservices.fec.service.ProductService;
import com.askamservices.fec.service.UserService;

@Controller
public class ProductController {

	@Autowired
	ProductService productService;
	@Autowired
	UserService userService;
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/product")
	public String showProduct(Model model) {
		
		List<Product> products = productService.listProducts();
		List<Category> categoryList = categoryService.listCategories();
		
		CustomUserDetail connectedUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User userId = userService.getUser(connectedUser.getUserId());
		model.addAttribute("userConnectedName", userId.getUsername());
		
		
		model.addAttribute("products", products);
		model.addAttribute("categoryList", categoryList);
		
		Product product = new Product();
		model.addAttribute("product", product);
	
		
		
		return "product";
	}
	
	/**
	 * This method permit to edit an existing product
	 * 
	 * */
	
	@GetMapping("/editProduct/{id}")
	public String updateProduct(@PathVariable("id") final Integer productId, Model model) {
		
		CustomUserDetail connectedUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User userId = userService.getUser(connectedUser.getUserId());
		model.addAttribute("userConnectedName", userId.getUsername());
		
		Product product = productService.getProduct(productId);
		List<Category> categoryList = categoryService.listCategories();
		
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("product", product);	
		
		return "formUpdateProduct";		
	}
	
	/**
	 * Update
	 * This method permit to update the edited category
	 * 
	 * */
	
	@PutMapping("/updateProduct")
	public Product productToUpdate(@RequestBody Product product) {
	
		Product prod = productService.getProduct(product.getProductId());
	
	if (prod != null) {
		
		Product currentProduct = prod;
		
		String productCategory = product.getCategoryId();
		if (productCategory != null) {
			currentProduct.setCategoryId(productCategory);
		}
		
		String productName = product.getProductName();
		if (productName != null) {
			currentProduct.setProductName(productName);
		}
		
		Integer productPrice = product.getPrice();
		if (productPrice != null) {
			currentProduct.setPrice(productPrice);
		}
		
		Integer productStock = product.getStock();
		if (productStock != null) {
			currentProduct.setStock(productStock);
		}
		
		Integer productSupplier = product.getSupplierId();
		if (productSupplier != null) {
			currentProduct.setSupplierId(productSupplier);
		}
		
		String productDesc = product.getProductDescription();
		if (productDesc != null) {
			currentProduct.setProductDescription(productDesc);
		}
		
		MultipartFile productImg = product.getProductImg();
		if (productImg != null) {
			currentProduct.setProductImg(productImg);
		}
		
		productService.saveProduct(currentProduct);
		
		return currentProduct;
		
	} else {
		return null; 
	}
}
	
	@PostMapping("/insertProduct")
	public ModelAndView insertProduct(@ModelAttribute Product product, @RequestParam("productImg") MultipartFile imgFile, Model model) {
		
		if (product.getProductId() != null) {
			
			productToUpdate(product);
			
		} else {
			
			productService.saveProduct(product);
		}
		
		/*
		 * On ecrit dans le fichier 'image' en utilisant des bytes '(buff)'
		 * aprèes telechargement du fichier image soumis
		 * 
		 * imagePath represente le repertoire dans lequel seront stockés 
		 * tous les fichiers images soumis
		 * 
		 * */
		
		String imagePath = "E:\\formationjava\\workspace\\FashionECommerceBackEnd\\src\\main\\resources\\static\\images\\";
		
		imagePath = imagePath + String.valueOf(product.getProductId() + ".jpg");
		
//		imagePath = imagePath + product.getProductName() + ".jpg";
		
		File image = new File(imagePath);
		
		if (!imgFile.isEmpty()) {
			
			try {
				
					byte buff[] = imgFile.getBytes();
					FileOutputStream fos = new FileOutputStream(image);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					bos.write(buff);
					bos.close();
				
			} catch (Exception e) {

				model.addAttribute("errorInfo", "Error Occured During Image Uploading: " + e.getMessage());
			}
			
		} else {model.addAttribute("errorInfo", "Problem Occured During Image Uploading");}
		
			
		return new ModelAndView("redirect:/product");
	}
	
	@GetMapping("/displayProducts") ///totalProductDisplay/{id}
	public String displayAllProducts(Model model) {
		
		List<Product> products = productService.listProducts();
		model.addAttribute("productList", products);
		
		CustomUserDetail connectedUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User userId = userService.getUser(connectedUser.getUserId());
		model.addAttribute("userConnectedName", userId.getUsername());
		
		return "productDisplay";
	}
	
	
	@GetMapping("/totalProductDisplay/{id}")
	public String totalProductDisplay(@PathVariable("id") Integer productId, Model model) {
		
		Product product = productService.getProduct(productId);
		model.addAttribute("product", product);
		
		CustomUserDetail connectedUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User userId = userService.getUser(connectedUser.getUserId());
		model.addAttribute("userConnectedName", userId.getUsername());
		
		return "totalProductDisplay";
	}
	
	
			
	@GetMapping("/deleteProduct/{id}")
	public ModelAndView showAllCategories(@PathVariable("id") Integer productId, Model model) {
		
		productService.deleteProduct(productId);
		
		return new ModelAndView("redirect:/product");
	
	}
}
