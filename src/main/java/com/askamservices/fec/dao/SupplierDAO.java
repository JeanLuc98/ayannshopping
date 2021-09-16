package com.askamservices.fec.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.askamservices.fec.model.Supplier;

public interface SupplierDAO extends JpaRepository<Supplier, Integer> {

}
