package com.askamservices.fec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.askamservices.fec.dao.UserDAO;
import com.askamservices.fec.model.CustomUserDetail;
import com.askamservices.fec.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userDAO.findByUsername(username);
		
		if (user == null) {
			
			throw new UsernameNotFoundException("User not found!");
		}
		
		return new CustomUserDetail(user);
	}

}
