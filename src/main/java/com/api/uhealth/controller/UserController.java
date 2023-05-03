package com.api.uhealth.controller;


import com.api.uhealth.classes.LoginRequest;
import com.api.uhealth.collections.User;
import com.api.uhealth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController (UserService userService){
        this.userService = userService;
    }



    @PostMapping("/login")
    public  ResponseEntity<?> Login(@RequestBody LoginRequest loginRequest){
        User isUser = userService.getUserByEmail(loginRequest.getEmail());
        if(isUser == null){
            return ResponseEntity.badRequest().body("Correo o contraseña incorrecto");
        }
        //Validar password
        if(!isUser.getPassword().equals(loginRequest.getPassword())){
            return ResponseEntity.badRequest().body("Correo o contraseña incorrecto");
        }

        return ResponseEntity.ok().body(isUser);

    }
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }
    @PostMapping("/")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result){
        //Validar si hay un campo con algun error
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }
        //Validar email
        if(userService.getUserByEmail(user.getEmail()) != null){
            return ResponseEntity.badRequest().body("El usuario ya se encuentra registrado");
        }

        User userCreated = userService.createUser(user);
        return ResponseEntity.ok(userCreated);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @Valid @RequestBody User userToUpdate, BindingResult result){
        if(result.hasErrors()) {
            // manejar errores de validación
            return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }
        User userFromDb = userService.findUserById(userId);
        User userUpdated = userService.updateUser(userFromDb, userToUpdate);
        return ResponseEntity.ok().body(userUpdated);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok().body("Usuario eliminado correctamente");
    }


}
