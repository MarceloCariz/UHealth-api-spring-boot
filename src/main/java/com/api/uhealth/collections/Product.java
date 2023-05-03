package com.api.uhealth.collections;


import jakarta.validation.constraints.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.NumberFormat;

@Document(collection = "products")
public class Product {


    @Id
    private String id;

    @NotBlank
    @NotEmpty(message = "El nombre del debe tener al menos 3 caracteres") //Valida un string si esta vacio
    @Size(min = 3, max = 20)
    @Indexed(unique = true)
    @Field("product_name")
    private String productName;

    @NotNull
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @PositiveOrZero(message = "Las calorias no pueden ser negativas")
    private float calories;

    @NotNull
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @PositiveOrZero(message = "Los carbohidratos no pueden ser negativos")
    @Field("carbohydrates")
    private float carbs;

//    @NotNull
//    @NotEmpty(message = "El categoryId no debe estar en blanco")
//    @Size(min = 3, max = 30)
//    @Field("category_id")
//    private String categoryId;

    @DBRef
    private Category category;

    //Todo: categoria id falta en producto
    public Product(String productName, float calories, float carbs,  Category category){
//        super();
        this.productName = productName.toLowerCase().trim();
        this.calories = calories;
        this.carbs = carbs;
//        this.categoryId = categoryId;
        this.category = category;
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

//    public void setCategoryId(String categoryId) {
//        this.categoryId = categoryId;
//    }


    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public String getId() {
        return id;
    }

//    public String getCategoryId() {
//        return categoryId;
//    }

    public String getProductName() {
        return productName;
    }

    public float getCalories() {
        return calories;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public float getCarbs() {
        return carbs;
    }
}
