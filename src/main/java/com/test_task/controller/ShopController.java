package com.test_task.controller;

import com.test_task.entity.Product;
import com.test_task.service.ProductService;
import com.test_task.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ShopController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ShopService shopService;

    private Long currentShop;

    @GetMapping("/shop/{id}")
    public String shop(@PathVariable Long id, Model model){
        model.addAttribute("products", productService.findByShopId(id));
        currentShop = id;
        return "/products/shop";
    }

    @GetMapping("/add")
    public String getAddForm(Model model){
        model.addAttribute("product", new Product());
        return "products/add";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "products/add";
        }

        product.setShop(shopService.findById(currentShop));

        productService.save(product);

        model.addAttribute("products", productService.findByShopId(currentShop));
        return "products/shop";
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable Long id, Model model){
        model.addAttribute("product", productService.findById(id));
        return "products/edit";
    }

    @PatchMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, @ModelAttribute("product") @Valid Product product, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("product", productService.findById(id));
            return "products/edit";
        }

        product.setId(id);
        product.setShop(shopService.findById(currentShop));

        productService.edit(product);

        model.addAttribute("products", productService.findByShopId(currentShop));
        return "products/shop";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteShop(@PathVariable Long id, Model model){
        productService.deleteById(id);

        model.addAttribute("products", productService.findByShopId(currentShop));
        return "products/shop";
    }


}
