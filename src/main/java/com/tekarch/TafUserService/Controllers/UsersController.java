package com.tekarch.TafUserService.Controllers;
import com.tekarch.TafUserService.DTO.UsersDTO;
import com.tekarch.TafUserService.Services.UsersServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UsersController {

    @Autowired
    private UsersServiceImpl userServiceImpl;

    //Get user by ID
    @GetMapping("/{user_id}")
    public ResponseEntity<UsersDTO> getUser(@PathVariable("user_id") Long user_id) {
        UsersDTO user = userServiceImpl.getUserById(user_id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
        //return ResponseEntity.ok(user);
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<UsersDTO>> getAllUsers() {
        List<UsersDTO> users = userServiceImpl.getAllUsers();
        return ResponseEntity.ok(users);
    }
    // Create a new user
    @PostMapping
    public ResponseEntity<UsersDTO> createUser(@RequestBody UsersDTO userDTO) {
        UsersDTO createdUser = userServiceImpl.addUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }

    // Update an existing user
    @PutMapping("/{user_id}")
    public ResponseEntity<UsersDTO> updateUser(@PathVariable("user_id") Long user_id, @RequestBody UsersDTO userDTO) {
        UsersDTO updatedUser = userServiceImpl.updateUserById(user_id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete a user by ID
    @DeleteMapping("/{user_id}")
    public ResponseEntity<String> deleteUser(@PathVariable("user_id") Long user_id) {
        return userServiceImpl.deleteUser(user_id);
       // return ResponseEntity.noContent().build();
    }
}