package com.tekarch.TafUserService.Services.Interface;

import com.tekarch.TafUserService.DTO.UsersDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UsersDTO addUser(UsersDTO user);
    UsersDTO getUserById(Long user_id);
    UsersDTO updateUserById(Long user_id, UsersDTO userDTO);
    List<UsersDTO> getAllUsers();
    ResponseEntity<String> deleteUser(Long user_id);

}
