package com.api.uhealth.classes;

import com.api.uhealth.collections.Routine;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

public class RoutineRequestUpdate extends Routine {


    @NotBlank(message = "El id del producto es obligatorio")
    @NotEmpty(message = "EL id del producto no puede estar vacio")
    private String productId;


    public RoutineRequestUpdate(Date date, String horario, String userId, String productId) {
        super(date, horario);
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
