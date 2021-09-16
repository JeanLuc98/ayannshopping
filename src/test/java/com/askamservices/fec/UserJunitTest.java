package com.askamservices.fec;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.askamservices.fec.model.User;
import com.askamservices.fec.service.UserService;

@SpringBootTest
class UserJunitTest {

	@Autowired
	UserService userService;
	
	@Test
	public void registerUserTest() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		User user = new User();
		
		user.setUsername("cascoco");
		user.setEmail("fulco@exemple.com");
		user.setPassword(encoder.encode("12345678"));
		user.setRole("user");
		user.setCustomerName("Franck");
		user.setCustomerAddr("Bouaké");
		
		userService.saveUser(user);
	}
	
	

}
