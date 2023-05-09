package com.api.uhealth.controller;


import com.api.uhealth.classes.*;
import com.api.uhealth.collections.Routine;
import com.api.uhealth.collections.User;
import com.api.uhealth.interfaces.UserGet;
import com.api.uhealth.security.JwtUtils;
import com.api.uhealth.service.UserService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController (UserService userService){
        this.userService = userService;
    }

    // Info jwt
    @GetMapping("/refresh-token")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        // Aquí puedes procesar el token y obtener la información del usuario
        String newToken = token.replace("Bearer ", "");

        Claims userInfo = JwtUtils.extractUserInfoFromToken(newToken);
        if (userInfo != null) {
            String email = userInfo.getSubject();
            String username = userInfo.get("nombre").toString();
            String role = userInfo.get("role").toString();
            Collection<? extends GrantedAuthority> roles = (Collection<? extends GrantedAuthority>) userInfo.get("roles");

            // Generar el nuevo token con la información actualizada
            String refreshedToken = JwtUtils.createToken(username, email, roles, role);
            // Crear un objeto JSON que contenga el nuevo token y la información del usuario
            Map<String, Object> response = new HashMap<>();
            response.put("token", refreshedToken);
            response.put("email", email);
            response.put("username", username);
            response.put("role", role);
            // Devolver la información del usuario en formato JSON
            return ResponseEntity.ok(response);
        } else {
            // Devolver una respuesta de error si no se pudo obtener la información del usuario
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
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
    public ResponseEntity<List<UserGet>> getAllUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }


    ///Crear usuario
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
    public ResponseEntity<?>  updateUser(@PathVariable String userId, @Valid @RequestBody  User userToUpdate, BindingResult result){
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
    //Rutina - Crear
    @PostMapping("/routine/create")
    public ResponseEntity<?> createRoutine(@Valid @RequestBody RoutineRequestCreate routineRequestCreate, BindingResult result){
        if(result.hasErrors()) {
            // manejar errores de validación
            return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }
        Routine newRoutine = userService.createRoutine(routineRequestCreate);
        return  ResponseEntity.ok().body(newRoutine);
    }


    @GetMapping("/routine/get/all/{userId}")
    public ResponseEntity<?> getAllRoutine(@PathVariable String userId){
        if(userService.findUserById(userId) == null){
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        List<Routine> routines = userService.getAllRoutineByUserId(userId);

        //si hay un unico elemento y este es null retorna un array vacio []
        if(routines.size() == 1 && routines.get(0) == null){
            return ResponseEntity.ok().body(new ArrayList<>());
        }

        return ResponseEntity.ok().body(routines);
    }



    @PutMapping("/routine/update/{routineId}")
    public ResponseEntity<?> updateRoutine(@PathVariable String routineId, @Valid @RequestBody RoutineRequestUpdate routineRequestUpdate){
        Routine routine = userService.updateRoutineById(routineRequestUpdate, routineId);
        return ResponseEntity.ok().body(routine);
    }

    @DeleteMapping("routine/delete/{routineId}")
    public ResponseEntity<?> deleteRoutine(@PathVariable String routineId){
        userService.DeleteRoutineById(routineId);
        return ResponseEntity.ok().body("Rutina eliminada correctamente");
    }


    @PostMapping("/routine/get/all/date")
    public ResponseEntity<?> getAllRoutinesByDate(@Valid @RequestBody RoutineRequestGetByDate routineRequestGetByDate){

        List<Routine> routines = userService.getAllRoutinesByDate(routineRequestGetByDate);
        return  ResponseEntity.ok().body(routines);
    }




}
