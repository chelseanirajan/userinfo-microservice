package com.cotiviti.userinfo.controller;

import com.cotiviti.userinfo.dto.UserDTO;
import com.cotiviti.userinfo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {


    UserServiceImpl userService;

    public UserController(UserServiceImpl userService){
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        UserDTO userDTO1 = userService.addUser(userDTO);
        return new ResponseEntity<>(userDTO1, HttpStatus.CREATED);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

}
