package com.test_task.controller;

import com.test_task.entity.Shop;
import com.test_task.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/chain")
public class ShopsController {

    @Autowired
    private ShopService shopService;

    @GetMapping("/shops")
    public String chain(Model model) {
        model.addAttribute("shops", shopService.findAll());
        return "chain/shops";
    }

    @GetMapping("/add")
    public String getAddForm(Model model){
        model.addAttribute("shop", new Shop());
        return "chain/add";
    }

    @PostMapping("/add")
    public String addShop(@ModelAttribute("shop") @Valid Shop shop, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "chain/add";
        }
        if (shopService.findByName(shop.getName()) != null){
            model.addAttribute("shopError", "Магазин вже існує");
            return "chain/add";
        }
        shopService.save(shop);

        model.addAttribute("shops", shopService.findAll());
        return "chain/shops";
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable Long id, Model model){
        model.addAttribute("shop", shopService.findById(id));
        return "chain/edit";
    }

    @PatchMapping("/edit/{id}")
    public String editShop(@PathVariable Long id, @ModelAttribute("shop") @Valid Shop shop, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "chain/edit";
        }

        shop.setId(id);
        shopService.edit(shop);

        model.addAttribute("shops", shopService.findAll());
        return "chain/shops";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteShop(@PathVariable Long id, Model model){
        shopService.deleteById(id);

        model.addAttribute("shops", shopService.findAll());
        return "chain/shops";
    }
}