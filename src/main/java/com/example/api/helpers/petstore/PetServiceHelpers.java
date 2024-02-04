package com.example.api.helpers.petstore;

import com.example.api.utils.ConfigManager;
import com.example.api.constants.EndPoints;
import com.example.api.model.petstore.Pet;
import com.example.api.model.petstore.Tag;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.*;

public class PetServiceHelpers {
    // read the config variables
    // url and port if any
    // making a get request and send the data to test
    private static final String BASE_URL = ConfigManager.getInstance().getString("baseUrl");

    /**
     * Constructor
     */
    public PetServiceHelpers(){
        RestAssured.baseURI= BASE_URL;
    }

    /**
     * Find Pets By status
     * @return list of pets by status.
     */
    public Response findPetsByStatus(String status){
    return  RestAssured.
            given().log().all()
            .contentType(ContentType.JSON)
            .queryParam("status", status)
            .get(EndPoints.GET_LIST_OF_PETS_BY_STATUS)
            .andReturn();
    }

    /**
     * Update Existing Pet for random id.
     * @param petId
     * @return response
     */
    public Response updateExistingPetAttributes(long petId){
        Pet pet = new Pet();
        List<Tag> listOfTags = new ArrayList<>();
        Tag tag= new Tag();
        tag.setId(1l);
        tag.setName("NewTag");
        listOfTags.add(tag);
        pet.setId(petId);
        pet.setName("NewPet");
        pet.setStatus(EndPoints.SOLD_STATUS);
        pet.setTags(listOfTags);

        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .body(pet)
                .put()
                .andReturn();
    }

    /**
     * findPetById
     * @param petId
     * @return
     */
    public Response findPetById(long petId){
         return RestAssured.
                given()
                .contentType(ContentType.JSON)
                .pathParam("id",petId)
                .get(EndPoints.GET_PETS_BY_ID)
                .andReturn();
    }

    /**
     * Get Random Pet Id
     * @param listOfPets
     * @return random id
     * @throws JsonProcessingException
     */
    public Long getRandomPetId(List<Pet> listOfPets) throws JsonProcessingException {
        // Convert list of pets to JSON string
        ObjectMapper mapper = new ObjectMapper();
        String jsonArrayString = mapper.writeValueAsString(listOfPets);
        // Deserialize JSON string back to list of maps
        List<Map<String, Object>> list = mapper.readValue(jsonArrayString, new TypeReference<List<Map<String, Object>>>() {});
        // Get random index
        int size = list.size();
        Random random = new Random();
        int randomIndex = random.nextInt(size);
        // Get random object and extract ID
        Long randomId = ((Number) list.get(randomIndex).get("id")).longValue();
        return randomId;
    }

    /**
     *
     * @param response
     * @param expectedStatusCode
     */
    public void assertStatusCode(Response response, int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
    }

    /**
     *
     * @param response
     * @param schemaFilePath
     */
    public void assertResponseBodyMatchesSchema(Response response, String schemaFilePath) {
        response.then().assertThat().body(matchesJsonSchemaInClasspath(schemaFilePath));
    }

    /**
     *
     * @param expectedValue
     * @param actualValue
     * @param <T>
     */
    public <T> void assertAnyFieldInResponse(T expectedValue, T actualValue) {
        assertEquals(expectedValue, actualValue);
    }
}