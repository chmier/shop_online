package com.example.projektKoncowy.controller;

import java.lang.reflect.Type;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.projektKoncowy.model.*;
import com.example.projektKoncowy.repository.CategoryRepository;
import com.example.projektKoncowy.repository.FurnitureRepository;
import com.example.projektKoncowy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
@Slf4j
public class CategoryController
{

    private final FurnitureRepository furnitureRepository;

    private CategoryRepository categoryRepository;

    private UserRepository userRepo;

    @Autowired
    public CategoryController(FurnitureRepository furnitureRepository, CategoryRepository categoryRepository, UserRepository userRepo) {
        this.furnitureRepository = furnitureRepository;
        this.categoryRepository = categoryRepository;
        this.userRepo = userRepo;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "design")
    public Category design() {
        return new Category();
    }

    @GetMapping
    public String showDesignForm(Model model, Principal principal) {
        log.info("   --- Designing category");
        List<Furniture> furnitures = new ArrayList<>();
        furnitureRepository.findAll().forEach(i -> furnitures.add(i));

        TypeOfFurniture[] types = TypeOfFurniture.values();
        for (TypeOfFurniture type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(furnitures, type));
        }

        String username = principal.getName();
        User user = userRepo.findByUsername(username);
        model.addAttribute("user", user);

        return "design";
    }

    @PostMapping
    public String processDesign(
            @Valid Category category, Errors errors,
            @ModelAttribute Order order) {

        log.info("   --- Saving taco");

        if (errors.hasErrors()) {
            return "design";
        }

        Category saved = categoryRepository.save(category);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }

    private List<Furniture> filterByType(
            List<Furniture> furnitures, TypeOfFurniture type) {
        return furnitures
                .stream()
                .filter(x -> x.getTypeOfFurniture().equals(type))
                .collect(Collectors.toList());
    }

}
