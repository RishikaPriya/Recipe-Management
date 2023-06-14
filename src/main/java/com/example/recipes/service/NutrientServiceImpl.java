package com.example.recipes.service;

import com.example.recipes.controller.exceptions.ErrorFetchingNutrient;
import com.example.recipes.controller.exceptions.UnableToParseNutrientServiceResponseException;
import com.example.recipes.model.dto.NutrientValue;
import com.example.recipes.model.entity.IngredientEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Objects;

@Service
public class NutrientServiceImpl implements NutrientService {
    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Value("${api.nutrient-service.url}")
    private String url;

    @Value("${api.nutrient-service.app-id}")
    private String appId;

    @Value("${api.nutrient-service.app-key}")
    private String appKey;

    @Autowired
    public NutrientServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public Map<String, NutrientValue> getNutrientValues(IngredientEntity ingredientEntity) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("app_id", appId)
                .queryParam("app_key", appKey)
                .queryParam("ingr", ingredientEntity.getQuantity() + " " + ingredientEntity.getName());

        try {
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(builder.toUriString(), JsonNode.class);
            JsonNode nutrientData = Objects.requireNonNull(response.getBody()).get("totalNutrientsKCal");

            try {
                return objectMapper.readValue(nutrientData.toString(), new TypeReference<>() {});
            } catch (JsonProcessingException exception) {
                throw new UnableToParseNutrientServiceResponseException(exception.getMessage());
            }
        } catch (Exception exception) {
            throw new ErrorFetchingNutrient("Error fetching nutrient " + exception);
        }

    }

}
