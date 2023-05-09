package com.api.uhealth.classes;

import com.api.uhealth.collections.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;


public class UserRequestUpdate extends User{


    @Override
    @JsonIgnore(false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    @JsonIgnore(false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public void setPassword(String password) {
        super.setPassword(password);
    }
}
