package utils;

import com.example.recipes.model.dto.NutrientValue;
import com.example.recipes.model.dto.Recipe;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TestUtils {

    public static Recipe getRecipeInput(String inputJsonFilePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File(inputJsonFilePath);
        return objectMapper.readValue(jsonFile, Recipe.class);
    }

    public static JsonNode getJsonNodeResponse(String responseJsonFilePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File(responseJsonFilePath);
        return objectMapper.readValue(jsonFile, JsonNode.class);
    }

    public static Map<String, NutrientValue> getNutrientValue(String jsonFilePAth) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File(jsonFilePAth);
        return objectMapper.readValue(jsonFile, new TypeReference<>() {});
    }
}
