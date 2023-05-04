package com.api.uhealth.classes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class RoutineRequestGetByDate {

    @NotBlank
    @NotEmpty(message = "El campo userId no debe estar vacio")
    private String userId;
    @NotBlank
    @NotEmpty(message = "El campo date no debe estar vacio")
    private String date;


    public RoutineRequestGetByDate(String userId, String date) {
        this.userId = userId;
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
