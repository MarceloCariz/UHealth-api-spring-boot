package com.api.uhealth.service;


import com.api.uhealth.collections.Category;
import com.api.uhealth.collections.Product;
import com.api.uhealth.collections.Profile;
import com.api.uhealth.collections.User;
import com.api.uhealth.repository.ProfileRepository;
import com.api.uhealth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service

public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;


    @Autowired
    public UserService(UserRepository userRepository, ProfileRepository profileRepository){
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }



    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


    public User createUser(User user){
        //crear perfil
        Profile newProfile = new Profile(0,0,0,0);
        profileRepository.save(newProfile);
        user.setProfile(newProfile);

        return userRepository.save(user);
    }

    public User updateUser(User userDatabase, User userToUpdate){
        Profile newProfile = profileRepository.findById(userDatabase.getProfile().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Perfil no encontrado"));;
        // Asignar valores al perfil nuevo
        newProfile.setAge(userToUpdate.getProfile().getAge());
        newProfile.setWeight(userToUpdate.getProfile().getWeight());
        newProfile.setHeight(userToUpdate.getProfile().getHeight());
        newProfile.setImc(userToUpdate.getProfile().getWeight() /  (userToUpdate.getProfile().getHeight() * userToUpdate.getProfile().getHeight()) );
        //Guardar en la bd el perfil actualizado
        profileRepository.save(newProfile);
        userDatabase.setProfile(newProfile);
        // Asignar atributos nuevos del usaurio
        userDatabase.setUsername(userToUpdate.getUsername());
        userDatabase.setEmail(userToUpdate.getEmail());
        userDatabase.setRolName(userToUpdate.getRolName());

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
}
