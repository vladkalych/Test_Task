package com.test_task.service;

import com.test_task.entity.Shop;
import com.test_task.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class ShopService implements IService<Shop>{

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ShopRepository shopRepository;

    @Override
    public void save(Shop shop) {
        shopRepository.save(shop);
    }

    @Override
    public void deleteById(Long id) {
        shopRepository.deleteById(id);
    }

    @Override
    public void edit(Shop shop) {
        save(shop);
    }

    @Override
    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    @Override
    public Shop findById(Long id) {
        Optional<Shop> userFromDb = shopRepository.findById(id);
        return userFromDb.orElse(new Shop());
    }

    public Shop findByName(String name){
        return shopRepository.findShopByName(name);
    }
}
