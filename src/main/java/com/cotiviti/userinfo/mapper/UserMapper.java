package com.cotiviti.userinfo.mapper;


import com.cotiviti.userinfo.dto.UserDTO;
import com.cotiviti.userinfo.entity.User;
import com.cotiviti.userinfo.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO mapUserToUserDTO(User user);
    User mapUserDTOToUser(UserDTO userDTO);
}
