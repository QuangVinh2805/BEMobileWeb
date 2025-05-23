package com.example.mobile_store.controller;


import com.example.mobile_store.models.User;
import com.example.mobile_store.repository.UserRepository;
import com.example.mobile_store.request.LoginRequest;
import com.example.mobile_store.request.UserRequest;
import com.example.mobile_store.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    public static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUser(){
        return userService.listAllUser();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User findUser(@PathVariable("id") long id) {
        return userService.findUser(id);
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<User> findUserLogin(@RequestBody LoginRequest request) {
        return userService.findUserLogin(request);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
        return userService.deleteUser(id);
    }
    @DeleteMapping("/deleteAll")
    public ResponseEntity<HttpStatus> deleteAllUser() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody UserRequest request) {
        return userService.createUser(request);
    }

}
