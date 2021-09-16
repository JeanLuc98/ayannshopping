package com.askamservices.fec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.askamservices.fec.dao.OrderDAO;
import com.askamservices.fec.model.OrderDetail;

@Service
public class OrderService {

	@Autowired
	private OrderDAO orderDAO;
	
	public OrderDetail saveOrder(OrderDetail order) {
		
		return orderDAO.save(order);
	}
	
	public boolean updateOrder(String username) {
		
		return orderDAO.updateOrder(username);
	}
}
