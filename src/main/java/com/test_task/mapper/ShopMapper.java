package com.test_task.mapper;

import com.test_task.dto.ShopDto;
import com.test_task.entity.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShopMapper {

    ShopMapper INSTANCE = Mappers.getMapper(ShopMapper.class);

    ShopDto toShopDto(Shop shop);

    Shop toShop(ShopDto shopDto);

}
