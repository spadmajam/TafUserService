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

    /*@Override -- working one
    public UsersDTO getUserById(Long user_id) {
        logger.info("Retrieving specific user details by user ID");
        String url = userRepoUrl + "/users/"+ user_id;
        return restTemplate.getForObject(url, UsersDTO.class);
    }*/

    @Override
    public UsersDTO getUserById(Long user_id) {
        String url = dataStoreMsUrl + "/users/" + user_id;
        ResponseEntity<UsersDTO> response = restTemplate.getForEntity(url, UsersDTO.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new UserNotFoundException("User not found with ID: " + user_id);
        }
    }

    public List<UsersDTO> getAllUsers() {
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
        String url = dataStoreMsUrl + "/users/" + user_id;
        restTemplate.put(url, userDTO);
        return getUserById(user_id);
    }

    // Delete a user by ID
    public void deleteUser(Long user_id) {
        String url = dataStoreMsUrl + "/users/" + user_id;
        restTemplate.delete(url);
    }
}

/*@Override
public UsersDTO addUser(UsersDTO userDTO) {
    String url = userRepoUrl + "/users";
    UsersDTO newUser = restTemplate.postForObject(url,userDTO,UsersDTO.class);
    return newUser;
}
    @Override
    public UsersDTO updateUserById(Long user_id, UsersDTO userDTO) {
        String url = userRepoUrl + "/users/"+ user_id;
        restTemplate.put(url, userDTO);
        return getUserById(user_id);
    }

    @Override
    public List<UsersDTO> getAllUsers() {
        String url = userRepoUrl + "/users";
        UsersDTO[] users = restTemplate.getForObject(url, UsersDTO[].class);
        return users != null ? Arrays.asList(users) : Collections.emptyList();
    }


  *//*  public List<UsersDTO> getAllUsers() {
        String url = DATASERVICE_URL + "/users";
        User[] users = restTemplate.getForObject(url, User[].class);
        return users != null ? Arrays.asList(users) : Collections.emptyList();
    }*//*

   *//* @Override
    public List<UsersDTO> getAllUsers() {
        restTemplate.get
        return List.of();
    }*//*

    @Override
    public void deleteUser(Long user_id) {
        String url = userRepoUrl + "/users/" + user_id;
        restTemplate.delete(url);
    }*/
