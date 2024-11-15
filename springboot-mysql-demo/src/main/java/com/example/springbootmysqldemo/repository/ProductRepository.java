

package com.example.springbootmysqldemo.repository;

import com.example.springbootmysqldemo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	// In ProductRepository.java
	public Iterable<Product> findByNameContainingIgnoreCase(String name);

}

