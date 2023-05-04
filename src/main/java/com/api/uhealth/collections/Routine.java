package com.api.uhealth.collections;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "routine")
public class Routine {
    @Id
    private String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern =  "yyyy-MM-dd")
    private Date date;

    @NotBlank
    @NotEmpty(message = "El horario no debe estar vacio")
    @Pattern(regexp = "^(mañana|tarde|noche)$", message = "El campo role solo puede contener los valores 'mañana','tarde' o 'noche'")
    private String horario;

    @DBRef
    private Product product;


    public Routine(){}
    public Routine(Date date, String horario, Product product) {
        this.date = date;
        this.horario = horario;
        this.product = product;
    }

    public Routine(Date date, String horario) {
        this.date = date;
        this.horario = horario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
