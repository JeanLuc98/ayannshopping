package com.askamservices.fec.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.askamservices.fec.model.CustomUserDetail;
import com.askamservices.fec.model.Product;
import com.askamservices.fec.model.User;
import com.askamservices.fec.service.ProductService;
import com.askamservices.fec.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/userhome")
	public String userHome(Model model, HttpSession session){
		
		List<Product> productsList = productService.listProducts();
		model.addAttribute("productsList", productsList);
		
		CustomUserDetail connectedUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User userId = userService.getUser(connectedUser.getUserId());
		model.addAttribute("userConnectedName", userId.getUsername());
		
		return "userHome"; 
	}
	
	@GetMapping("/adminhome")
	public String adminHome(Model model, HttpSession session){
		
		CustomUserDetail connectedUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User userId = userService.getUser(connectedUser.getUserId());
		model.addAttribute("userConnectedName", userId.getUsername());
		
		return "adminHome"; 
	}
	
	@PostMapping("/success")
	public String userRegistration(@ModelAttribute User user, @RequestParam("userIcon") MultipartFile userIcon ,Model model) {
		
		userService.saveUser(user);	
		
		String imagePath = "E:\\formationjava\\workspace\\FashionECommerceBackEnd\\src\\main\\resources\\static\\favicon.ico\\icons\\";
		
		imagePath = imagePath + String.valueOf(user.getUsername() + ".jpg");
		
//		imagePath = imagePath + product.getProductName() + ".jpg";
		
		File image = new File(imagePath);
		
		if (!userIcon.isEmpty()) {
			
			try {
				
					byte buff[] = userIcon.getBytes();
					FileOutputStream fos = new FileOutputStream(image);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					bos.write(buff);
					bos.close();
				
			} catch (Exception e) {

				model.addAttribute("errorInfo", "Error Occured During Image Uploading: " + e.getMessage());
			}
			
		} else {model.addAttribute("errorInfo", "Problem Occured During Image Uploading");}
		
		return "successPage";
	}
	
	//Login form with error
	@GetMapping("/login_error")
	public String loginErrorPage(Model model) {
		
		model.addAttribute("loginError", true);
		
		return "login";
	}
	
	
	@GetMapping("/login_success")
	public String loginCheck(Model model, HttpSession session, Authentication authentication) {
		
		Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
				
		String page ="";

		
		for (GrantedAuthority role : roles) {
			
			
			if (role.getAuthority().equals("ROLE_ADMIN")) {
				
				page = "redirect:/adminhome";
				
			} else {
				
				page = "redirect:/userhome";
				
			}

		}
		
		return page;
	}

}
