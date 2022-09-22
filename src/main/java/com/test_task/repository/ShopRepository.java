package com.test_task.repository;

import com.test_task.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    Shop findShopByName(String name);

}
