package com.cotiviti.userinfo.service;

import com.cotiviti.userinfo.dto.UserDTO;
import com.cotiviti.userinfo.entity.User;
import com.cotiviti.userinfo.mapper.UserMapper;
import com.cotiviti.userinfo.repository.UserRepository;
import com.cotiviti.userinfo.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testAddUser(){
        UserDTO userdto = new UserDTO(1, "Nirajan", "nirajan@123", "texas", "roanoke");
        User user = UserMapper.INSTANCE.mapUserDTOToUser(userdto);
        when(userRepository.save(user)).thenReturn(user);

        UserDTO userDTO = userService.addUser(userdto);
        verify(userRepository, times(1)).save(user);
        assertEquals(userdto, userDTO);
    }
    @Test
    public void testGetUserById(){
        int id = 1 ;
        User user = new User(1, "Nirajan", "nirajan@123", "texas", "roanoke");
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        ResponseEntity<UserDTO> userDTOResponseEntity = userService.getUserById(id);
        verify(userRepository, times(1)).findById(id);
        assertEquals(HttpStatus.OK, userDTOResponseEntity.getStatusCode());
        assertEquals(id, userDTOResponseEntity.getBody().getUserId());


    }
    @Test
    public void testGetUserByIdNotId(){
        int id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        ResponseEntity<UserDTO> response = userService.getUserById(id);
        verify(userRepository, times(1)).findById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}
