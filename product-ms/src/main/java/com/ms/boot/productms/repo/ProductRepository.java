package com.ms.boot.productms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.ms.boot.productms.model.Product;

@Component
public interface ProductRepository extends JpaRepository<Product, Integer> {
	//List<Product> findByName(String name);
}
