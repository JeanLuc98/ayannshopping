package com.askamservices.fec.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.askamservices.fec.model.CartItem;
import com.askamservices.fec.model.CustomUserDetail;
import com.askamservices.fec.model.Product;
import com.askamservices.fec.model.User;
import com.askamservices.fec.service.CartItemService;
import com.askamservices.fec.service.ProductService;
import com.askamservices.fec.service.UserService;

@Controller
public class CartController {
	
	@Autowired
	CartItemService cartItemService;
	@Autowired
	UserService userService;
	@Autowired
	ProductService productService;
	
	@GetMapping("/cart")
	public String showCart(Model model) {
		
		List<CartItem> cartItemList = cartItemService.listCartItems();
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("grandTotal", this.getGrandTotal(cartItemList));
		
		CustomUserDetail connectedUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User userId = userService.getUser(connectedUser.getUserId());
		model.addAttribute("userConnectedName", userId.getUsername());
		
		for (CartItem cartItems : cartItemList) {
			
			model.addAttribute("cartItem", cartItems);
			
		}
		
		return "cartItem";
	}
	
	@PostMapping("/addToCart/{productId}")
	public String addToCart(@PathVariable("productId") Integer productId, @RequestParam("quantity") Integer quantity, Model model, HttpSession session) {
		
		String username = (String) session.getAttribute("username");
		Product product = productService.getProduct(productId);
		
		CartItem cartItem = new CartItem();
		cartItem.setProductId(product.getProductId());
		cartItem.setProductName(product.getProductName());
		cartItem.setQuantity(quantity);
		cartItem.setTotalPrice(product.getPrice());
		cartItem.setPaymentStatus("NP");
		cartItem.setUsername(username);
		
		cartItemService.saveCartItem(cartItem);
		
		return "redirect:/cart";
		 
	}
	
//	@GetMapping("/updateCartItem/{id}")
//	public String updateCartItem(@PathVariable("id") Integer cartItemId, @RequestParam("quantity") Integer quantity, Model model ){
//		
//		CartItem cart = cartItemService.getCartItem(cartItemId);
//		
//		cart.setQuantity(quantity);
//		
//		cartItemService.saveCartItem(cart);
//		
//		return "redirect:/cart";
//	}
//	
	@GetMapping("/deleteCartItem/{id}")
	public ModelAndView deleteCartItem(@PathVariable("id") Integer cartItemId, Model model ){
		
		cartItemService.deleteCartItem(cartItemId);
		 
		return new ModelAndView("redirect:/cart");
	}
	
	/*
	 * Cette methode permet de calculer le montant total des produits achetés
	 * 
	 * */
	
	private int getGrandTotal(List<CartItem> cartList) {
		
		int grandTotal = 0; int count = 0;
		
		while (count < cartList.size()) {
			
			grandTotal = grandTotal + (cartList.get(count).getQuantity() * cartList.get(count).getTotalPrice());
			count++;
		}
		
		return grandTotal;
	}

}
