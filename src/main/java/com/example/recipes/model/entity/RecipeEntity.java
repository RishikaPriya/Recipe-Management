package com.example.recipes.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeEntity {

    @Id
    private UUID id;

    private String name;

    private String description;

    private int cookingTime;

    private int servingSize;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    private List<IngredientEntity> ingredients;

    private List<String> instructions;

}
