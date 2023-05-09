package com.api.uhealth.service;


import com.api.uhealth.classes.RoutineRequestCreate;
import com.api.uhealth.classes.RoutineRequestGetByDate;
import com.api.uhealth.classes.RoutineRequestUpdate;
import com.api.uhealth.classes.UserRequestUpdate;
import com.api.uhealth.collections.*;
import com.api.uhealth.interfaces.UserGet;
import com.api.uhealth.repository.ProductRepository;
import com.api.uhealth.repository.ProfileRepository;
import com.api.uhealth.repository.RoutineRepository;
import com.api.uhealth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    private final RoutineRepository routineRepository;

    private final ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, ProfileRepository profileRepository, RoutineRepository routineRepository, ProductRepository productRepository){
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.routineRepository = routineRepository;
        this.productRepository = productRepository;
    }



    public List<UserGet> getAllUsers(){
        return userRepository.findAllBy();
    }


    public User createUser(User user){
        // Encriptar contrasena
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        //crear perfil
        Profile newProfile = new Profile(0,0,0,0);
        profileRepository.save(newProfile);
        user.setProfile(newProfile);
        user.setRoutines(new ArrayList<>());
        User userCreated = userRepository.save(user);

        // Entrgar solo usuario generado
        return userCreated;
    }

    public User updateUser(User userDatabase, User userToUpdate){
//        Profile newProfile = profileRepository.findById(userDatabase.getProfile().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Perfil no encontrado"));;
//        if(userToUpdate.getProfile() != null){
//            // Asignar valores al perfil nuevo
//            newProfile.setAge(userToUpdate.getProfile().getAge());
//            newProfile.setWeight(userToUpdate.getProfile().getWeight());
//            newProfile.setHeight(userToUpdate.getProfile().getHeight());
//            newProfile.setImc(userToUpdate.getProfile().getWeight() /  (userToUpdate.getProfile().getHeight() * userToUpdate.getProfile().getHeight()) );
//            //Guardar en la bd el perfil actualizado
//            profileRepository.save(newProfile);
//            userDatabase.setProfile(newProfile);
//        }

        // Asignar atributos nuevos del usaurio
        userDatabase.setUsername(userToUpdate.getUsername());
        userDatabase.setEmail(userToUpdate.getEmail());
        userDatabase.setRolName(userToUpdate.getRolName());
        //Encriptar password
//        if(userToUpdate.getPassword() != null){
//            String encodedPassword = passwordEncoder.encode(userToUpdate.getPassword());
//            userDatabase.setPassword(encodedPassword);
//        }


        return userRepository.save(userDatabase);
    }

    public void deleteUser(String userId){
        User user = findUserById(userId);
        userRepository.deleteById(userId);
    }
    public User findUserById(String userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuario no encontrado"));
        return user;
    }

    public User getUserByEmail(String email){
        User user = userRepository.findUserByEmail(email);
        return user;
    }

    /// Rutinas

    public List<Routine> getAllRoutineByUserId(String userId){
        User user = findUserById(userId);
        List<Routine> routines = user.getRoutines();
        return routines;
    }

    public List<Routine> getAllRoutinesByDate(RoutineRequestGetByDate routineRequestGetByDate){
        List<Routine> routines = getAllRoutineByUserId(routineRequestGetByDate.getUserId());
        //Obtener Rutinas del usuario por fecha
        List<Routine> filteredRoutines = filterRoutinesByDate(routines, routineRequestGetByDate.getDate());
        return filteredRoutines;
    }
    public Routine createRoutine(RoutineRequestCreate routineRequestCreate){
        User user = findUserById(routineRequestCreate.getUserId());
        //Buscar producto
        Product product = productRepository.findById(routineRequestCreate.getProductId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Producto no encontrado"));
        //Crear rutina
        Routine routine = new Routine(routineRequestCreate.getDate(), routineRequestCreate.getHorario(), product);

        //Guardar rutina
        Routine newRoutine = routineRepository.save(routine);
        //Get rutinas
        List<Routine> routines = user.getRoutines();
        //add rutina al listado
        routines.add(newRoutine);
        //Guardar rutina en el usuario
        user.setRoutines(routines);
        userRepository.save(user);

        return newRoutine;
    }

    public Routine updateRoutineById(RoutineRequestUpdate routineRequestUpdate, String routineId){
        Routine routineFromDB = findRoutineById(routineId);
        //Buscar producto
        Product newProduct = productRepository.findById(routineRequestUpdate.getProductId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Producto no encontrado"));;
        routineFromDB.setDate(routineRequestUpdate.getDate());
        routineFromDB.setHorario(routineRequestUpdate.getHorario());
        routineFromDB.setProduct(newProduct);
        //Guardar producto
        Routine routine = routineRepository.save(routineFromDB);
        return  routine;
     }

    public void DeleteRoutineById(String routineId){
        Routine routine = findRoutineById(routineId);
        routineRepository.delete(routine);
    }

    public Routine findRoutineById(String routineId){
        Routine routine = routineRepository.findById(routineId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Rutina no encontrada"));;
        return routine;
    }


    private List<Routine> filterRoutinesByDate(List<Routine> routines , String date){


            for(Routine routine :  routines){
                System.out.println(routine.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
                System.out.println(routine.getDate());
            }

            List<Routine> filteredRoutines = routines.stream().filter(routine
                            -> {
                        return routine.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString().equals(date.toString().trim());
                    })
                    .collect(Collectors.toList());

        return filteredRoutines;

    }

}
