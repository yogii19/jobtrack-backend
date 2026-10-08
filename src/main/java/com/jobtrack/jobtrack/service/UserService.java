package com.jobtrack.jobtrack.service;

import com.jobtrack.jobtrack.dto.UserDTO;
import com.jobtrack.jobtrack.exception.ResourceNotFoundException;
import com.jobtrack.jobtrack.model.User;
import com.jobtrack.jobtrack.Repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE USER
    public UserDTO createUser(UserDTO userDTO) {

        User user = new User();

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        User savedUser = userRepository.save(user);

        return convertToDTO(savedUser);
    }

    // GET ALL USERS
    public List<UserDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // GET USER BY ID
    public UserDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        return convertToDTO(user);
    }

    // UPDATE USER
    public UserDTO updateUser(
            Long id,
            UserDTO userDTO) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        User updatedUser =
                userRepository.save(user);

        return convertToDTO(updatedUser);
    }

    // DELETE USER
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        userRepository.delete(user);
    }

    // CONVERT ENTITY TO DTO
    private UserDTO convertToDTO(User user) {

        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        return dto;
    }
}