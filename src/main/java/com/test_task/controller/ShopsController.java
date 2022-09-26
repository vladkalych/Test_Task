package com.test_task.controller;

import com.test_task.dto.ShopDto;
import com.test_task.entity.Shop;
import com.test_task.dto.mapper.ShopMapper;
import com.test_task.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/chain")
public class ShopsController {

    @Autowired
    private ShopService shopService;

    @GetMapping("/shops")
    public String chain(Model model) {
        model.addAttribute("shopDtoList", getShopDtoList());
        return "chain/shops";
    }

    @GetMapping("/add")
    public String getAddForm(Model model){
        model.addAttribute("shopDto", new ShopDto());
        return "chain/add";
    }

    @PostMapping("/add")
    public String addShop(@ModelAttribute("shopDto") @Valid ShopDto shopDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "chain/add";
        }

        Shop shop = ShopMapper.INSTANCE.toShop(shopDto);

        if (shopService.findByName(shop.getName()) != null){
            model.addAttribute("shopError", "Магазин вже існує");
            return "chain/add";
        }
        shopService.save(shop);

        model.addAttribute("shopDtoList", getShopDtoList());
        return "chain/shops";
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable Long id, Model model){
        Shop shop = shopService.findById(id);
        ShopDto shopDto = ShopMapper.INSTANCE.toShopDto(shop);
        model.addAttribute("shopDto", shopDto);
        return "chain/edit";
    }

    @PatchMapping("/edit/{id}")
    public String editShop(@PathVariable Long id, @ModelAttribute("shopDto") @Valid ShopDto shopDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "chain/edit";
        }

        Shop shop = ShopMapper.INSTANCE.toShop(shopDto);

        shop.setId(id);
        shopService.edit(shop);

        model.addAttribute("shopDtoList", getShopDtoList());
        return "chain/shops";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteShop(@PathVariable Long id, Model model){
        shopService.deleteById(id);

        model.addAttribute("shopDtoList", getShopDtoList());
        return "chain/shops";
    }

    private List<ShopDto> getShopDtoList() {
        List<Shop> shopList = shopService.findAll();
        List<ShopDto> shopDtoList = new ArrayList<>();

        for (Shop shop : shopList) {
            shopDtoList.add(ShopMapper.INSTANCE.toShopDto(shop));
        }
        return shopDtoList;
    }
}