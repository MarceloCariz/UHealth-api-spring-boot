package com.api.uhealth.collections;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;

public class Rol {
    @Id
    private String id;

    @NotBlank
    @NotNull
    @NotEmpty(message = "El nombre del debe tener al menos 3 caracteres") //Valida un string si esta vacio
    @Size(min = 3, max = 20, message = "El nombre debe ser entre 3 y 20 caracteres")
    private String rolName;


    public Rol(String rolName) {
        this.rolName = rolName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }
}
