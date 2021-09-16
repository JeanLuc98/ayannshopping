package com.askamservices.fec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.askamservices.fec.dao.UserDAO;
import com.askamservices.fec.model.User;

@Service
public class UserService{

	@Autowired
	private UserDAO userDAO;
	
	BCryptPasswordEncoder password = new BCryptPasswordEncoder();
	
	public User saveUser(User user) {
		
		user.setRole(user.getRole().toUpperCase());
		user.setCustomerName(user.getCustomerName().toUpperCase());
		user.setPassword(password.encode(user.getPassword()));
		
		return userDAO.save(user);
	}
	
	public User getUser(Integer userId) {
		
		return userDAO.getById(userId);
	}
	
	public void deleteUser(Integer userId) {
		
		userDAO.deleteById(userId);
	}

	
	public User findByUsername(String username) {
		
		return userDAO.findByUsername(username);
	}
	
}
