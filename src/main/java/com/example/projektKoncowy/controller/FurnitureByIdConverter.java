package com.example.projektKoncowy.controller;


import java.util.Optional;

import com.example.projektKoncowy.model.Furniture;
import com.example.projektKoncowy.repository.FurnitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
class FurnitureByIdConverter implements Converter<String, Furniture> {

    private FurnitureRepository furnitureRepository;

    @Autowired
    public FurnitureByIdConverter(FurnitureRepository furnitureRepository) {
        this.furnitureRepository = furnitureRepository;
    }

    @Override
    public Furniture convert(String id) {
        Optional<Furniture> optionalIngredient = furnitureRepository.findById(id);
        return optionalIngredient.isPresent() ?
                optionalIngredient.get() : null;
    }

}
