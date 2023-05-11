package com.api.uhealth.Routine;

import com.api.uhealth.classes.RoutineRequestCreate;
import com.api.uhealth.collections.Product;
import com.api.uhealth.collections.Routine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoutineControllerTest {
    @LocalServerPort
    private int port;

    private String baseUrl ="http://localhost";

    private String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBjb3JyZW8uY29tIiwiZXhwIjoxNjg2MzIzNzY3LCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiYWRtaW5pc3RyYWRvciJ9XSwicm9sZSI6ImFkbWluaXN0cmFkb3IiLCJpZCI6IjY0NWIxNGJjY2ViNzlkNDdkMmI3ZWM1MyIsIm5vbWJyZSI6Im1hcmNlbG9jYXJpeiJ9.DAI6Z8Lcx9ST5gTaGnWDpX4Ot1sRGvuXtQ5eePxoElw";


    private String userId = "645b14bcceb79d47d2b7ec53";

    private String productId = "6452c8d1e7f1ad1d1c219d05";

    private static RestTemplate restTemplate;

    private HttpHeaders httpHeaders;

    private String dateString = "2023-05-11";

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    private Date defaultDate = null;

    private LocalDateTime defaultDateTime = null;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @BeforeAll
    public static void init(){
        restTemplate = new RestTemplate();
    }


    @BeforeEach
    public void setup(){
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/user/routine/");
        httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + token);

        try {
            defaultDate = dateFormat.parse(dateString);
        }catch (ParseException e){
            System.err.println(e.getMessage());
        }

    }

    @DisplayName("Junit create routine")
    @Test
    public void testCreateRoutine(){


        RoutineRequestCreate defaultRoutine = new RoutineRequestCreate(defaultDate,"noche", userId, productId);
        System.out.println("defaultDate" + defaultDate);
        HttpEntity<RoutineRequestCreate> routineHttpEntity = new HttpEntity<>(defaultRoutine, httpHeaders);

        ResponseEntity<Routine> routineResponse = restTemplate.exchange(baseUrl.concat("create"), HttpMethod.POST, routineHttpEntity ,Routine.class);

        Date dateResponseFormatted = routineResponse.getBody().getDate();
        try {
           dateResponseFormatted = dateFormat.parse(dateResponseFormatted.toString());
        }catch (ParseException e){
            System.err.println(e.getMessage());
        }

        //Comparar horarios
        assertEquals(defaultRoutine.getHorario(), routineResponse.getBody().getHorario());

        //Comparar fecha
        assertEquals(defaultDate, dateResponseFormatted);

    }
}
