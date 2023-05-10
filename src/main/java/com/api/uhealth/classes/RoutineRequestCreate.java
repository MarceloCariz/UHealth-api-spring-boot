package com.api.uhealth.classes;

import com.api.uhealth.collections.Routine;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class RoutineRequestCreate extends Routine {
    @NotBlank(message = "El id de usuario es obligatorio")
    @NotEmpty(message = "EL id usuario no puede estar vacio")
    @NotNull
    private String userId;

    @NotBlank(message = "El id del producto es obligatorio")
    @NotEmpty(message = "EL id del producto no puede estar vacio")
    @NotNull
    private String productId;


    public RoutineRequestCreate(Date date, String horario, String userId, String productId) {
        super(date, horario);
        this.userId = userId;
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
