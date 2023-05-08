package com.api.uhealth.Users;

import com.api.uhealth.collections.Profile;
import com.api.uhealth.collections.User;
import com.api.uhealth.repository.ProductRepository;
import com.api.uhealth.repository.ProfileRepository;
import com.api.uhealth.repository.RoutineRepository;
import com.api.uhealth.repository.UserRepository;
import com.api.uhealth.service.UserService;
import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.BDDMockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrl ="http://localhost";

    private String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBjb3JyZW8uY29tIiwiZXhwIjoxNjg2MTUwMTk2LCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiYWRtaW5pc3RyYWRvciJ9XSwicm9sZSI6ImFkbWluaXN0cmFkb3IiLCJub21icmUiOiJhZG1pbiJ9.OIsTitMZwNneldSv7TqIkhsgr9JcPVEP4zQnZcMIrVQ";

    private static RestTemplate restTemplate;

    private HttpHeaders httpHeaders;

    private User testUser;

    private HttpEntity<User>  requestUserEntity;

    @BeforeAll
    public static void init(){
        restTemplate = new RestTemplate();
    }


    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RoutineRepository routineRepository;

    @BeforeEach
    public void setup(){
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/user/");
        userService = new UserService(userRepository, profileRepository, routineRepository, productRepository);
        httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + token);
        //Crear usuario de pruebas
        User userDefault = new User("test", "test@correo.com", "12345678" , "usuario");
        requestUserEntity = new HttpEntity<>(userDefault, httpHeaders);

        testUser = restTemplate.postForObject(baseUrl ,requestUserEntity, User.class);
    }






    @DisplayName("JUnit test for create user method")
    @Test
    public void testCreateUser() {

        ResponseEntity<Void> responseDelete = restTemplate.exchange(baseUrl + "/" + testUser.getId(), HttpMethod.DELETE, requestUserEntity, Void.class);
//        System.out.println(response.getBody());
        // Verificar email
        assertEquals("test@correo.com", testUser.getEmail());
        // Verificar username
        assertEquals("test", testUser.getUsername());
        // Verificar rol
        assertEquals("usuario" , testUser.getRolName());
        // Verificar si se elimno
        assertEquals(HttpStatus.OK, responseDelete.getStatusCode());

    }

    @DisplayName("JUnit test for update user method")
    @Test
    public void testUpdateUser(){

        /// Crear usuario a actualizar
//        HttpEntity<User> requestEntity = new HttpEntity<>(testUser, httpHeaders);
//
//        User originalUser = restTemplate.postForObject(baseUrl ,requestEntity, User.class);


        User modifiedUser = new User("modified", "modified@correo.com", "123456789" , "usuario");

        HttpEntity<User> requestEntityModifedUser = new HttpEntity<>(modifiedUser, httpHeaders);
        ResponseEntity<User> updatedUser = restTemplate.exchange(baseUrl  + testUser.getId(),HttpMethod.PUT , requestEntityModifedUser, User.class);


        //eliminar
        ResponseEntity<Void> response = restTemplate.exchange(baseUrl + testUser.getId(), HttpMethod.DELETE, requestUserEntity,  Void.class);

//        System.out.println("uu"+updatedUser.getBody().getUsername() + "original" + originalUser.getUsername());
        //Verficar que los username no son iguales
        assertNotEquals(testUser.getUsername(), updatedUser.getBody().getUsername());
        //Verficar que los emails no son iguales
        assertNotEquals(testUser.getEmail(), updatedUser.getBody().getEmail());

    }


    @DisplayName("JUnit test for delete user method")
    @Test
    public void testDeleteUser(){
        HttpEntity<?> requestEntity = new HttpEntity<>( httpHeaders);
        ResponseEntity<Void> response = restTemplate.exchange(baseUrl + testUser.getId(), HttpMethod.DELETE, requestEntity,  Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
