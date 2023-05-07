package com.api.uhealth.collections;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
public class User  {

    @Id
    private String id;

    @NotBlank
    @NotNull
    @NotEmpty(message = "El nombre del debe tener al menos 3 caracteres") //Valida un string si esta vacio
    @Size(min = 3, max = 20, message = "El nombre debe ser entre 3 y 20 caracteres")
    private String username;


    @NotBlank
    @NotEmpty(message = "El nombre del debe tener al menos 3 caracteres")
    @Email(message = "El email no es válido")
    @Indexed(unique = true)
    private String email;

    @NotBlank
    @NotEmpty(message = "La contraseña no debe estar vacía")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8 , max = 20, message = "La contraseña debe ser de mínimo 8 o un máximo de 20 caracteres")
    private String password;

    @NotBlank
    @NotEmpty(message = "El rol no puede estar vacío")
    @Pattern(regexp = "^(usuario|administrador)$", message = "El campo role solo puede contener los valores 'usuario' o 'administrador'")
    private String rolName;
    @DBRef
    private Profile profile;

    @DBRef
    private List<Routine> routines;




    public User( String username, String email, String password, String rolName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.rolName = rolName;
    }

//    public User( String username, String email, String rolName, Profile profile) {
//        this.username = username;
//        this.email = email;
//        this.rolName = rolName;
//        this.profile = profile;
//    }

    public List<Routine> getRoutines() {
        return routines;
    }

    public void setRoutines(List<Routine> routines) {
        this.routines = routines;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
