package com.api.uhealth.collections;


import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "products")
public class Product {


    @Id
    private String id;

    @NotBlank
    @NotEmpty(message = "El nombre del debe tener al menos 3 caracteres") //Valida un string si esta vacio
    @Size(min = 3, max = 20)
    @Field("product_name")
    private String productName;

    @NotNull()
    @PositiveOrZero(message = "Las calorias no pueden ser negativas")
    private float calories;

    @NotNull()
    @PositiveOrZero(message = "Los carbohidratos no pueden ser negativos")
    @Field("carbohydrates")
    private float carbs;


    @Field("category_id")
    @DBRef
    private String categoryId;

    //Todo: categoria id falta en producto
    public Product(String productName, float calories, float carbs, String categoryId){
//        super();
        this.productName = productName.toLowerCase().trim();
        this.calories = calories;
        this.carbs = carbs;
        this.categoryId = categoryId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public float getCalories() {
        return calories;
    }

    public float getCarbs() {
        return carbs;
    }
}
