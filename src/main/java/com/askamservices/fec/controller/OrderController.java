package com.askamservices.fec.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.askamservices.fec.model.CartItem;
import com.askamservices.fec.model.CustomUserDetail;
import com.askamservices.fec.model.OrderDetail;
import com.askamservices.fec.model.User;
import com.askamservices.fec.service.CartItemService;
import com.askamservices.fec.service.OrderService;
import com.askamservices.fec.service.UserService;

@Controller
public class OrderController {

	@Autowired
	private CartItemService cartItemService ;
//	@Autowired
//	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/checkout")
	public String checkOut(Model model, HttpSession session) {
		
		List<CartItem> cartItemList = cartItemService.listCartItems();
		CustomUserDetail connectedUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User userId = userService.getUser(connectedUser.getUserId());
		
		model.addAttribute("userConnectedName", userId.getUsername());
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("grandTotal", this.getGrandTotal(cartItemList));
		
		return "order";
	}
	
	@PostMapping("/updateAddr")
	public String updateAddress(@RequestParam(value = "address") String addr, Model model, HttpSession session) {
		
		List<CartItem> cartItemList = cartItemService.listCartItems();
		
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("grandTotal", this.getGrandTotal(cartItemList));
		
		User user =new User();
//		user.setCustomerAddr(addr);
//		userService.saveUser(user);
		
		String address = user.getCustomerAddr();
		
		model.addAttribute("address", address);
		
		return "order";
	}
	
	@GetMapping("/payment")
	public String payment(Model model) {
		
		CustomUserDetail connectedUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User userId = userService.getUser(connectedUser.getUserId());
		model.addAttribute("userConnectedName", userId.getUsername());
		model.addAttribute("userConnectedFullName", userId.getCustomerName());
		
		return "paymentForm";
	}
	
	@PostMapping("/receipt")
	public String generateReceipt(@RequestParam("btnRD") String rd , Model model, HttpSession session) {
		
//		String username = (String) session.getAttribute("username");
		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrderDate(new Date());
		orderDetail.setShippingAddr(null);
		orderDetail.setTransaction(rd);
		orderDetail.setUsername(null);
		
		
		List<CartItem> cartItemList = cartItemService.listCartItems();
		CustomUserDetail connectedUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User userId = userService.getUser(connectedUser.getUserId());
		
		model.addAttribute("userConnectedName", userId.getUsername());
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("grandTotal", this.getGrandTotal(cartItemList));
		
		orderDetail.setTotalAmount(this.getGrandTotal(cartItemList));
		
		orderService.saveOrder(orderDetail);
		
		model.addAttribute("orderDetail", orderDetail);
		
		return "receipt";
	}
	
	private int getGrandTotal(List<CartItem> cartList) {
		
		int grandTotal = 0; int count = 0;
		
		while (count < cartList.size()) {
			
			grandTotal = grandTotal + (cartList.get(count).getQuantity() * cartList.get(count).getTotalPrice());
			count++;
		}
		
		return grandTotal;
	}
}
