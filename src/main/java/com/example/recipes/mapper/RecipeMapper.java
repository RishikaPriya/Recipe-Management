package com.example.recipes.mapper;

import com.example.recipes.model.dto.Ingredient;
import com.example.recipes.model.dto.Recipe;
import com.example.recipes.model.entity.IngredientEntity;
import com.example.recipes.model.entity.RecipeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RecipeMapper {
    @Mapping(target = "id", expression = "java(getEntityId())")
    RecipeEntity toEntity(Recipe recipe);

    @Mapping(target = "id", expression = "java(getEntityId())")
    IngredientEntity toIngredientEntity(Ingredient ingredient);

    void updateEntityFromDto(Recipe recipeRequest, UUID id, @MappingTarget RecipeEntity recipe);

    default UUID getEntityId() {
        return UUID.randomUUID();
    }
}
