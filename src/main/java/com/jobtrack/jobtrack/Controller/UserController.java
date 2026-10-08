package com.jobtrack.jobtrack.Controller;

import com.jobtrack.jobtrack.dto.UserDTO;
import com.jobtrack.jobtrack.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<UserDTO> createUser(
            @Valid @RequestBody UserDTO userDTO) {

        return ResponseEntity.ok(
                userService.createUser(userDTO)
        );
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {

        return ResponseEntity.ok(
                userService.getAllUsers()
        );
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                userService.getUserById(id)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO) {

        return ResponseEntity.ok(
                userService.updateUser(id, userDTO)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}