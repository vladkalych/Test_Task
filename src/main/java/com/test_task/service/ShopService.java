package com.test_task.service;

import com.test_task.entity.Shop;
import com.test_task.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class StoreService implements IService<Shop>{

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private StoreRepository storeRepository;

    @Override
    public boolean save(Shop shop) {
        Shop shopFromDB = storeRepository.findStoreByName(shop.getName());

        if (shopFromDB != null) {
            return false;
        }

        storeRepository.save(shop);
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        if (storeRepository.findById(id).isPresent()) {
            storeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean edit(Shop shop) {
        if(!deleteById(shop.getId())){
            return false;
        }
        save(shop);
        return true;
    }

    @Override
    public List<Shop> findAll() {
        return storeRepository.findAll();
    }

    @Override
    public Shop findById(Long id) {
        Optional<Shop> userFromDb = storeRepository.findById(id);
        return userFromDb.orElse(new Shop());
    }
}
