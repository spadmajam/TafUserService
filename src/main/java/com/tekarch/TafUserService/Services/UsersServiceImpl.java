package com.tekarch.TafUserService.Services;

import com.tekarch.TafUserService.DTO.UsersDTO;
import com.tekarch.TafUserService.Exceptions.UserNotFoundException;
import com.tekarch.TafUserService.Services.Interface.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UsersServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UsersServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${tafdatastorems.url}")
    private String dataStoreMsUrl;

    @Override
    public UsersDTO getUserById(Long user_id) {
        logger.info("Get user by ID:: ");
        String url = dataStoreMsUrl + "/users/" + user_id;
        ResponseEntity<UsersDTO> response = restTemplate.getForEntity(url, UsersDTO.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new UserNotFoundException("User not found with ID: " + user_id);
        }
    }

    public List<UsersDTO> getAllUsers() {
        logger.info("Get all users ");
        String url = dataStoreMsUrl + "/users";
        ResponseEntity<UsersDTO[]> response = restTemplate.getForEntity(url, UsersDTO[].class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return List.of(response.getBody());
        } else {
            throw new RuntimeException("Failed to fetch users");
        }
    }

    // Create a new user
    public UsersDTO addUser(UsersDTO userDTO) {
        logger.info("Create user::");
        String url = dataStoreMsUrl + "/users";
        ResponseEntity<UsersDTO> response = restTemplate.postForEntity(url, userDTO, UsersDTO.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to create user");
        }
    }

    // Update an existing user by ID
    public UsersDTO updateUserById(Long user_id, UsersDTO userDTO) {
        logger.info("Update user by ID:: ");
        String url = dataStoreMsUrl + "/users/" + user_id;
        restTemplate.put(url, userDTO);
        return getUserById(user_id);
    }

    public ResponseEntity<String> deleteUser(Long user_id) {
        logger.info("Delete user by ID:: " + user_id);
        String url = dataStoreMsUrl + "/users/" + user_id;

        try {
            restTemplate.delete(url);
            return ResponseEntity.ok("User with ID " + user_id + " was successfully deleted.");
        } catch (HttpClientErrorException e) {
            logger.error("Error deleting user with ID " + user_id, e);
            return ResponseEntity.status(e.getStatusCode())
                    .body("Failed to delete user with ID " + user_id + ": " + e.getMessage());
        }

   /* // Delete a user by ID
    public void deleteUser(Long user_id) {
        logger.info("Delete user by ID:: ");
        String url = dataStoreMsUrl + "/users/" + user_id;
        restTemplate.delete(url);
    }*/
    }
}

