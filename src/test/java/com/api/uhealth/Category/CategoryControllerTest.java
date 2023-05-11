package com.api.uhealth.Category;


import com.api.uhealth.collections.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest {
    @LocalServerPort
    private int port;

    private String baseUrl ="http://localhost";

    private static RestTemplate restTemplate;

    private String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBjb3JyZW8uY29tIiwiZXhwIjoxNjg2MzIzNzY3LCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiYWRtaW5pc3RyYWRvciJ9XSwicm9sZSI6ImFkbWluaXN0cmFkb3IiLCJpZCI6IjY0NWIxNGJjY2ViNzlkNDdkMmI3ZWM1MyIsIm5vbWJyZSI6Im1hcmNlbG9jYXJpeiJ9.DAI6Z8Lcx9ST5gTaGnWDpX4Ot1sRGvuXtQ5eePxoElw";


    private HttpHeaders httpHeaders;

    @BeforeAll
    public static void init(){
        restTemplate = new RestTemplate();
    }


    @BeforeEach
    public void setup(){
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/category/");
        httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + token);
    }

    @DisplayName("JUnit create category")
    @Test
    public void testCreateCategory(){
        Category defaultCategory = new Category("testCategory");
        HttpEntity<Category> requestEntityCategory = new HttpEntity<>(defaultCategory, httpHeaders);

        Category  responseCategory = restTemplate.postForObject(baseUrl, requestEntityCategory, Category.class);

        ResponseEntity<Void> responseDelete = restTemplate.exchange(baseUrl + "/" + responseCategory.getId(), HttpMethod.DELETE, new HttpEntity<>(httpHeaders), Void.class);

        //se crea en minusculas el nombre de la categoria
        assertEquals(responseCategory.getCategoryName(),defaultCategory.getCategoryName().toLowerCase());
    }

    @DisplayName("JUnit update category")
    @Test
    public void testUpdateCategory(){
        //Crear
        Category defaultCategory = new Category("testCategory");
        HttpEntity<Category> requestEntityCategory = new HttpEntity<>(defaultCategory, httpHeaders);

        Category  responseCategory = restTemplate.postForObject(baseUrl, requestEntityCategory, Category.class);

        // Actualizar
        Category updatedCategory = new Category("updatedCategory");
        HttpEntity<Category> requestUpdateEntityCategory = new HttpEntity<>(updatedCategory, httpHeaders);

        ResponseEntity<Category> updatedProductResponse = restTemplate.exchange(baseUrl + responseCategory.getId(), HttpMethod.PUT  ,requestUpdateEntityCategory , Category.class);
        ResponseEntity<Void> responseDelete = restTemplate.exchange(baseUrl + "/" + responseCategory.getId(), HttpMethod.DELETE, new HttpEntity<>(httpHeaders), Void.class);


        assertNotEquals(updatedCategory.getCategoryName(), responseCategory.getCategoryName());

    }

    @DisplayName("JUnit delete category")
    @Test
    public void testDeleteCategory(){
        //Crear
        Category defaultCategory = new Category("testCategory");
        HttpEntity<Category> requestEntityCategory = new HttpEntity<>(defaultCategory, httpHeaders);

        Category  responseCategory = restTemplate.postForObject(baseUrl, requestEntityCategory, Category.class);
        //Eliminar
        ResponseEntity<Void> response = restTemplate.exchange(baseUrl + "/" + responseCategory.getId(), HttpMethod.DELETE, new HttpEntity<>(httpHeaders),  Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}















