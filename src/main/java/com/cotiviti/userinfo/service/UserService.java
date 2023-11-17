package com.cotiviti.userinfo.service;


import com.cotiviti.userinfo.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public UserDTO addUser(UserDTO userDTO);
    public ResponseEntity<UserDTO> getUserById(int id);
}
