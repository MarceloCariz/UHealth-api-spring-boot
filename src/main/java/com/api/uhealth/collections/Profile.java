package com.api.uhealth.collections;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.NumberFormat;

@Document(collection = "profile")
public class Profile {

    @Id
    private String id;


    @NotNull
    @NumberFormat(style = NumberFormat.Style.DEFAULT)
    @PositiveOrZero(message = "La edad no puede ser negativa")
    private int age;

    @NotNull
    @NumberFormat(style = NumberFormat.Style.DEFAULT)
    @PositiveOrZero(message = "El peso no puede ser negativo")
    private float weight;
    @NotNull
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @PositiveOrZero(message = "El peso no puede ser negativo")
    private float height;

    private float imc;

    public Profile( int age, float weight, float height, float imc) {
//        this.id = id;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.imc = imc;
    }

    public Profile(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getImc() {
        return imc;
    }

    public void setImc(float imc) {
        this.imc = imc;
    }
}
