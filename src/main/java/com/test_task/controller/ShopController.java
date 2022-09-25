package com.test_task.controller;

import com.test_task.dto.ProductDto;
import com.test_task.entity.Product;
import com.test_task.mapper.ProductMapper;
import com.test_task.service.ProductService;
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
@RequestMapping("/products")
public class ShopController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ShopService shopService;

    private Long currentShop;

    @GetMapping("/shop/{id}")
    public String shop(@PathVariable Long id, Model model){
        model.addAttribute("productDtoList", getProductDtoList(id));
        currentShop = id;
        return "/products/shop";
    }

    @GetMapping("/add")
    public String getAddForm(Model model){
        model.addAttribute("productDto", new ProductDto());
        return "products/add";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("productDto") @Valid ProductDto productDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "products/add";
        }

        Product product = ProductMapper.INSTANCE.toProduct(productDto);
        product.setShop(shopService.findById(currentShop));

        productService.save(product);

        model.addAttribute("productDtoList", getProductDtoList(currentShop));
        return "products/shop";
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable Long id, Model model){
        Product product = productService.findById(id);
        ProductDto productDto = ProductMapper.INSTANCE.toProductDto(product);
        model.addAttribute("productDto", productDto);
        return "products/edit";
    }

    @PatchMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, @ModelAttribute("productDto") @Valid ProductDto productDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("product", productService.findById(id));
            return "products/edit";
        }

        Product product = ProductMapper.INSTANCE.toProduct(productDto);
        product.setId(id);
        product.setShop(shopService.findById(currentShop));

        productService.edit(product);

        model.addAttribute("productDtoList", getProductDtoList(currentShop));
        return "products/shop";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteShop(@PathVariable Long id, Model model){
        productService.deleteById(id);
        model.addAttribute("productDtoList", getProductDtoList(currentShop));
        return "products/shop";
    }

    private List<ProductDto> getProductDtoList(Long id) {
        List<Product> productList = productService.findByShopId(id);
        List<ProductDto> productDtoList = new ArrayList<>();

        for (Product product : productList) {
            productDtoList.add(ProductMapper.INSTANCE.toProductDto(product));
        }
        return productDtoList;
    }


}
