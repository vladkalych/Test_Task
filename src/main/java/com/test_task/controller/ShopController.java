package com.test_task.controller;

import com.test_task.entity.Product;
import com.test_task.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @GetMapping("/shop/{id}")
    public String shop(@PathVariable Long id, Model model){
        model.addAttribute("products", productService.findByShopId(id));
        return "/products/shop";
    }

    @GetMapping("/add")
    public String getAddForm(Model model){
        model.addAttribute("product", new Product());
        return "products/add";
    }

    @PostMapping("/add")
    public String addShop(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "products/add";
        }
        if (!productService.save(product)){
            model.addAttribute("productError", "Товар вже існує");
            return "products/add";
        }

        model.addAttribute("products", productService.findByShopId(product.getShop().getId()));
        return "products/shop";
    }



}
