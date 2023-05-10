package com.api.uhealth.classes;

import com.api.uhealth.collections.User;
import com.api.uhealth.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;


public class UserRequestUpdate{

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
    @NotEmpty(message = "El rol no puede estar vacío")
    @Pattern(regexp = "^(usuario|administrador)$", message = "El campo role solo puede contener los valores 'usuario' o 'administrador'")
    private String rolName;

    public UserRequestUpdate(String username, String email, String rolName) {
        this.username = username;
        this.email = email;
        this.rolName = rolName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }
}
