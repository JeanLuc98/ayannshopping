package com.askamservices.fec.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.askamservices.fec.model.CartItem;

@Repository
public interface CartDAO extends JpaRepository<CartItem, Integer> {

}
