package com.test_task.controller;

import com.test_task.entity.Store;
import com.test_task.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/chain")
public class ChainController {

    @Autowired
    StoreService storeService;

    @GetMapping("/chain")
    public String chain(Model model) {
        model.addAttribute("stores", storeService.findAll());
        return "chain/chain";
    }

    @GetMapping("/add")
    public String getAddForm(Model model){
        model.addAttribute("store", new Store());
        return "chain/add";
    }

    @PostMapping("/add")
    public String addStore(@ModelAttribute("store") @Valid Store store, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "chain/add";
        }
        if (!storeService.save(store)){
            model.addAttribute("storeError", "Магазин вже існує");
            return "chain/add";
        }

        model.addAttribute("stores", storeService.findAll());
        return "chain/chain";
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable Long id, Model model){
        model.addAttribute("store", storeService.findById(id));
        return "chain/edit";
    }

    @PatchMapping("/edit/{id}")
    public String editStore(@PathVariable Long id, @ModelAttribute("store") @Valid Store store, Model model){
        store.setId(id);
        if (!storeService.edit(store)){
            // Error
        }

        model.addAttribute("stores", storeService.findAll());
        return "chain/chain";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStore(@PathVariable Long id, Model model){
        if (!storeService.deleteById(id)){
            // Error
        }

        model.addAttribute("stores", storeService.findAll());
        return "chain/chain";
    }
}