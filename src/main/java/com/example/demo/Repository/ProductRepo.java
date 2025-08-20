package com.example.demo.Repository;

import com.example.demo.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Long> {
    Page<Product> findByProductNameLike(String keyword, Pageable pageDetails);

}
