package com.test_task.service;

import com.test_task.entity.Product;
import com.test_task.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IService<Product> {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }


    @Override
    public void edit(Product product) {
        save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByShopId(Long id){
        return productRepository.findAllByShopId(id);
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> userFromDb = productRepository.findById(id);
        return userFromDb.orElse(new Product());
    }
}
