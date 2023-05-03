package com.api.uhealth.controller;


import com.api.uhealth.collections.Product;
import com.api.uhealth.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product, BindingResult result){
        //Validar si hay un campo con algun error
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        Product productSave = productService.createProduct(product);
        return new ResponseEntity<Product>(productSave, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id){
        Product product = productService.getProductById(id);
        if(product == null){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }


}

//        try{

//            return productSave;
//        }catch (Exception e){
//            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR); // En caso de error getCause "Toma la causa"
//        }