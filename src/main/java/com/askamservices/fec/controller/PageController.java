package com.askamservices.fec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.askamservices.fec.model.CustomUserDetail;
import com.askamservices.fec.model.User;
import com.askamservices.fec.service.UserService;

@Controller
public class PageController {
	
	@Autowired
	UserService userService;

	@GetMapping("/")
	public String homePage() {
		
		return "home";
	}
	
	@GetMapping("/login")
	public String loginPage() {
		
		return "login";
	}
	
	@GetMapping("/logout")
	public String logoutPage() {
		
		return "login";
	}
	
	@GetMapping("/register")
	public String registerPage(Model model) {
		
		User user = new User();
		model.addAttribute("user", user);
		
		return "register";
	}
	
	@GetMapping("/contactus")
	public String contactPage(Model model) {
		
		return "contact";
	}
	
	@GetMapping("/aboutus")
	public String infoPage(Model model) {
		
		return "info";
	}
	
	@GetMapping("/contact")
	public String contact2Page(Model model) {
		
		CustomUserDetail connectedUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User userId = userService.getUser(connectedUser.getUserId());
		model.addAttribute("userConnectedName", userId.getUsername());
		
		return "contact-U";
	}
	
	@GetMapping("/about")
	public String info2Page(Model model) {
		
		CustomUserDetail connectedUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User userId = userService.getUser(connectedUser.getUserId());
		model.addAttribute("userConnectedName", userId.getUsername());
		
		return "info-U";
	}
}
