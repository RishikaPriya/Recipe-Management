package com.example.recipes.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientEntity {

    @Id
    private UUID id;

    private String name;

    private String quantity;

}
