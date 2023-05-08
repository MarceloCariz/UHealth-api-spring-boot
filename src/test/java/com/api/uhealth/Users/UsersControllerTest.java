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
    }


    @DisplayName("JUnit test for create user method")
    @Test
    @After()
    public void testCreateUser() {

        User user = new User("test", "test@correo.com", "12345678" , "usuario");

        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + token);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);

        User response = restTemplate.postForObject(baseUrl ,requestEntity, User.class);
        ResponseEntity<Void> responseDelete = restTemplate.exchange(baseUrl + "/" + response.getId(), HttpMethod.DELETE, requestEntity, Void.class);
//        System.out.println(response.getBody());
        // Verificar email
        assertEquals("test@correo.com", response.getEmail());
        // Verificar username
        assertEquals("test", response.getUsername());
        // Verificar rol
        assertEquals("usuario" , user.getRolName());
        // Verificar si se elimno
        assertEquals(HttpStatus.OK, responseDelete.getStatusCode());


    }
}
