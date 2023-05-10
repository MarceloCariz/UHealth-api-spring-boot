package com.api.uhealth.Products;


import com.api.uhealth.collections.Category;
import com.api.uhealth.collections.Product;
import com.api.uhealth.collections.User;
import com.api.uhealth.service.UserService;
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
public class ProductsControllerTest {
    @LocalServerPort
    private int port;

    private String baseUrl ="http://localhost";

    private String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBjb3JyZW8uY29tIiwiZXhwIjoxNjg2MzIzNzY3LCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiYWRtaW5pc3RyYWRvciJ9XSwicm9sZSI6ImFkbWluaXN0cmFkb3IiLCJpZCI6IjY0NWIxNGJjY2ViNzlkNDdkMmI3ZWM1MyIsIm5vbWJyZSI6Im1hcmNlbG9jYXJpeiJ9.DAI6Z8Lcx9ST5gTaGnWDpX4Ot1sRGvuXtQ5eePxoElw";

    private static RestTemplate restTemplate;

    private HttpHeaders httpHeaders;

    @BeforeAll
    public static void init(){
        restTemplate = new RestTemplate();
    }


    @BeforeEach
    public void setup(){
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/products/");
        httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + token);
    }

    @DisplayName("JUnit create Product")
    @Test
    public void testProductCreate(){

        Product defaultProduct = new Product("productTest", 20, 20);
        HttpEntity<Product> requestUserEntity = new HttpEntity<>(defaultProduct, httpHeaders);
        /// id categoria verduras / 6452a5828371cf0f2032b8c7
        String categoryId = "6452a5828371cf0f2032b8c7"; // Verduras

        ResponseEntity<Product> testProduct = restTemplate.exchange(baseUrl + "/" + categoryId,HttpMethod.POST ,requestUserEntity, Product.class);

        ResponseEntity<Void> responseDelete = restTemplate.exchange(baseUrl + "/" + testProduct.getBody().getId(), HttpMethod.DELETE, requestUserEntity, Void.class);
//        System.out.println(response.getBody());
        // Verificar productName / transforma a minusculas
        assertEquals("producttest", testProduct.getBody().getProductName());
        // Verificar calorias
        assertEquals(20, testProduct.getBody().getCalories());
        // Verificar carbs
        assertEquals(20 , testProduct.getBody().getCarbs());
        // Verificar nombre categoria
        assertEquals("verduras", testProduct.getBody().getCategory().getCategoryName());
    }

    @DisplayName("JUnit update Product")
    @Test
    public void testProductUpdate(){
        Product defaultProduct = new Product("productTest", 20, 20);
        HttpEntity<Product> requestProductEntity = new HttpEntity<>(defaultProduct, httpHeaders);
        /// id categoria verduras / 6452a5828371cf0f2032b8c7
        String categoryId = "6452a5828371cf0f2032b8c7"; // Verduras
        ResponseEntity<Product> testProduct = restTemplate.exchange(baseUrl + "/" + categoryId , HttpMethod.POST ,requestProductEntity, Product.class);


        Product modifiedProduct = new Product("productTestModified", 30, 30);
        //asignar category id

        modifiedProduct.setCategory(testProduct.getBody().getCategory());

        HttpEntity<Product> requestEntityModifiedProduct = new HttpEntity<>(modifiedProduct, httpHeaders);

        ResponseEntity<Product> modifiedProductResponse = restTemplate.exchange(baseUrl + testProduct.getBody().getId() , HttpMethod.PUT ,requestEntityModifiedProduct, Product.class);

        ResponseEntity<Void> responseDelete = restTemplate.exchange(baseUrl + "/" + testProduct.getBody().getId(), HttpMethod.DELETE, requestProductEntity, Void.class);

        //Comparar que el nombre no sea igual
        assertNotEquals(modifiedProductResponse.getBody().getProductName(), testProduct.getBody().getProductName());
        //Comparar que las calorias no sean igual
        assertNotEquals(modifiedProductResponse.getBody().getCalories(), testProduct.getBody().getCalories());
        //Comparar que los carbs no sean igual
        assertNotEquals(modifiedProductResponse.getBody().getCarbs(),  testProduct.getBody().getCarbs());

    }

    @DisplayName("Junit delete Product")
    @Test
    public void testDeleteProduct(){
        Product defaultProduct = new Product("productTest", 20, 20);
        HttpEntity<Product> requestProductEntity = new HttpEntity<>(defaultProduct, httpHeaders);
        /// id categoria verduras / 6452a5828371cf0f2032b8c7
        String categoryId = "6452a5828371cf0f2032b8c7"; // Verduras
        ResponseEntity<Product> testProduct = restTemplate.exchange(baseUrl + "/" + categoryId , HttpMethod.POST ,requestProductEntity, Product.class);

        ResponseEntity<Void> response = restTemplate.exchange(baseUrl + "/" + testProduct.getBody().getId(), HttpMethod.DELETE, requestProductEntity,  Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
