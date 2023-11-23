package com.cotiviti.userinfo.service;

import com.cotiviti.userinfo.dto.UserDTO;
import com.cotiviti.userinfo.entity.User;
import com.cotiviti.userinfo.mapper.UserMapper;
import com.cotiviti.userinfo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        User save = userRepository.save(UserMapper.INSTANCE.mapUserDTOToUser(userDTO));
        return UserMapper.INSTANCE.mapUserToUserDTO(save);
    }

    @Override
    public ResponseEntity<UserDTO> getUserById(int id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            return new ResponseEntity<>(UserMapper.INSTANCE.mapUserToUserDTO(byId.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
