package com.askamservices.fec.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.askamservices.fec.model.OrderDetail;

@Repository
public interface OrderDAO extends JpaRepository<OrderDetail, Integer> {
	
	@Query(value = "update CartItem set paymentStatus = 'P' where username=:uname")
	boolean updateOrder(String uname) ;
}
