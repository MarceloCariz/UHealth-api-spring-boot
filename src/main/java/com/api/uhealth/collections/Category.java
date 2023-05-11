package com.api.uhealth.collections;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "category")
public class Category {
    @Id
    private String id;


    @NotBlank
    @NotEmpty(message = "El nombre del debe tener al menos 3 caracteres") //Valida un string si esta vacio
    @Pattern(regexp = "^[a-zA-Z]+$", message = "El nombre de la categoría debe contener solo letras")
    @Size(min = 3, max = 20)
    @Indexed(unique = true)
    @Field("category_name")
    private String categoryName;


    public Category(){} //Solucion al error: https://stackoverflow.com/questions/69538090/json-parse-error-cannot-construct-instance-of-although-at-least-one-creator-e
    public Category(String id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName.toLowerCase().trim();
    }

    public Category(String categoryName){
        this.categoryName = categoryName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName =  categoryName.toLowerCase().trim();
    }



}
