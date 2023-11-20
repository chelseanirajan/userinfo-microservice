package com.cotiviti.userinfo.controller;

import com.cotiviti.userinfo.dto.UserDTO;
import com.cotiviti.userinfo.entity.User;
import com.cotiviti.userinfo.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUser(){
        UserDTO user = new UserDTO(1, "Nirajan", "niraja@123", "texas", "roanoke");

        when(userService.addUser(user)).thenReturn(user);

        ResponseEntity<UserDTO> response = userController.addUser(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).addUser(user);
    }
    @Test
    public void testGetUserById(){
        int id = 1;
        UserDTO user = new UserDTO(1, "Nirajan", "niraja@123", "texas", "roanoke");

        when(userService.getUserById(id)).thenReturn(new ResponseEntity<>(user, HttpStatus.OK));
        ResponseEntity<UserDTO> response = userService.getUserById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());

        verify(userService, times(1)).getUserById(id);
    }
}
