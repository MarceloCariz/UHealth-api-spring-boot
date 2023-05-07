package com.api.uhealth.controller;


import com.api.uhealth.collections.Category;
import com.api.uhealth.collections.Product;
import com.api.uhealth.service.ProductService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
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


    @GetMapping("/")
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id){
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/category/{categoryId}")
    public  List<Product> getProductsByCategoryId(@PathVariable String categoryId){
        return productService.getAllProductsByCategoryId(categoryId);
    }

    @PostMapping("/{categoryId}")
    public ResponseEntity<?> createProduct(@PathVariable String categoryId,@Valid @RequestBody Product product, BindingResult result){
        //Validar si hay un campo con algun error
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        // Validar si existe el campo
        if(productService.findByProductName(product)){
            return new ResponseEntity<>("El producto ya existe", HttpStatus.BAD_REQUEST);
        }
        //Validar Categoria
        Category isCategory = productService.findCategoryById(categoryId);
        if(isCategory == null){
            return new ResponseEntity<>("La categoria no existe", HttpStatus.BAD_REQUEST);
        }
        product.setCategory(isCategory);
        Product productSave = productService.createProduct(product);

        return new ResponseEntity<Product>(productSave, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @Valid @RequestBody Product updatedProduct, BindingResult result) {
        if(result.hasErrors()) {
            // manejar errores de validación
            return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }
        //Validar si existe el producto
        Product isProduct = productService.getProductById(id);
        if(isProduct == null){
            return new ResponseEntity<>("EL producto no existe", HttpStatus.BAD_REQUEST);
        }
                                                        //Producto con nueva info, producto directamente desde la bd
        Product productUpdated = productService.updateProduct(updatedProduct, isProduct);
        return new ResponseEntity<Product>(productUpdated, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){
        productService.deleteProduct(id);
        return new ResponseEntity<>("Producto eliminado correctamente", HttpStatus.CREATED);
    }



}

