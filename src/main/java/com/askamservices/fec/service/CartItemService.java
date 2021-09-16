package com.askamservices.fec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.askamservices.fec.dao.CartDAO;
import com.askamservices.fec.model.CartItem;

@Service
public class CartItemService{
	
	@Autowired
	private CartDAO cartDAO;
	
	public List<CartItem> listCartItems(){
		
		return cartDAO.findAll();
		
	}
	
	public CartItem getCartItem(Integer cartItemId){
		
		return cartDAO.findById(cartItemId).get();
	}

	
	public void deleteCartItem(Integer cartItemId) {
		
		cartDAO.deleteById(cartItemId);
	}
	
	public CartItem saveCartItem(CartItem cartItem) {
		
		return cartDAO.save(cartItem);
	}
	

}
