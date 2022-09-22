package com.test_task.repository;

import com.test_task.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductByName(String name);
    List<Product> findAllByShopId(Long id);

}
