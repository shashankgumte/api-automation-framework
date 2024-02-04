package com.example.api.tests.petstore;

import com.example.api.constants.EndPoints;
import com.example.api.helpers.petstore.PetServiceHelpers;
import com.example.api.model.petstore.Pet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.util.List;

public class TestPetStoreAPIs {

    private PetServiceHelpers petServiceHelpers;
    private static final String schemaFilePath ="petstore/Response_FindPetByStatus.json";
    @BeforeClass
    public void init(){
        petServiceHelpers = new PetServiceHelpers();
    }

    @Test
    @Owner("Shashank")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Integration test to check pet store APIs")
    public void testGetAllAvailablePets() throws JsonProcessingException {
        // Find Pets By Status Available
        Response findPetsByStatusResponse = petServiceHelpers.findPetsByStatus(EndPoints.AVAILABLE_STATUS);
        petServiceHelpers.assertStatusCode(findPetsByStatusResponse,HttpStatus.SC_OK);
        Type type = new TypeReference<List<Pet>>(){}.getType();
        List<Pet> petList = findPetsByStatusResponse.as(type);
        petServiceHelpers.assertResponseBodyMatchesSchema(findPetsByStatusResponse,schemaFilePath);
        // Get random pet id from response
        long petID=petServiceHelpers.getRandomPetId(petList);
        System.out.println("random pet id "+ petID);
        // For above pet id update few attributes like name, status and tag
        Response updateExistingPetAttributesResponse = petServiceHelpers.updateExistingPetAttributes(petID);
        petServiceHelpers.assertStatusCode(updateExistingPetAttributesResponse,HttpStatus.SC_OK);
        // get the id key value and compare with random petID
        long id= updateExistingPetAttributesResponse.jsonPath().get("id");
        String status = updateExistingPetAttributesResponse.jsonPath().get("status");
        String name = updateExistingPetAttributesResponse.jsonPath().get("name");
        String tagName = updateExistingPetAttributesResponse.jsonPath().get("tags[0].name");

        // assertEquals(id,petID);
        petServiceHelpers.assertAnyFieldInResponse(id,petID);
        petServiceHelpers.assertAnyFieldInResponse(name,"NewPet");
        petServiceHelpers.assertAnyFieldInResponse(tagName,"NewTag");
        petServiceHelpers.assertAnyFieldInResponse(status,EndPoints.SOLD_STATUS);
        // Use get pet by id API
        Response findPetByIdResponse =petServiceHelpers.findPetById(id);
        petServiceHelpers.assertStatusCode(findPetByIdResponse,HttpStatus.SC_OK);
    }
}