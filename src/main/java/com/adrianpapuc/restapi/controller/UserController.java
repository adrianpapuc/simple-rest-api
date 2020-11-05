package com.adrianpapuc.restapi.controller;

import com.adrianpapuc.restapi.system.ResourceNotFoundException;
import com.adrianpapuc.restapi.entity.User;
import com.adrianpapuc.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // get all users
    @GetMapping
    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }
    // get user by id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") long userId){
        return userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User with id: "+ userId + " not found"));
    }
    // create user
    @PostMapping
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }
    // update user
    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user,@PathVariable(value = "id") long userId){
        User currentUser = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User with id: "+ userId + " not found"));
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setEmail(user.getEmail());
        return userRepository.save(currentUser);
    }
    // delete user by id
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") long userId){
        userRepository.delete(userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User with id: "+ userId + " not found")));
        return ResponseEntity.ok().build();
    }

}
