package com.jobtrack.jobtrack.service;

import com.jobtrack.jobtrack.dto.LoginRequest;
import com.jobtrack.jobtrack.dto.RegisterRequest;
import com.jobtrack.jobtrack.dto.UserDTO;
import com.jobtrack.jobtrack.exception.InvalidCredentialsException;
import com.jobtrack.jobtrack.model.User;
import com.jobtrack.jobtrack.Repository.UserRepository;
import com.jobtrack.jobtrack.security.JwtService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // =========================
    // REGISTER
    // =========================

    public UserDTO register(RegisterRequest request) {

        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {

            throw new RuntimeException(
                    "Email is already registered"
            );
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // Encrypt password
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        // Save user to MySQL
        User savedUser =
                userRepository.save(user);

        // Debug information
        System.out.println("================================");
        System.out.println("USER REGISTERED SUCCESSFULLY");
        System.out.println("ID: " + savedUser.getId());
        System.out.println("NAME: " + savedUser.getName());
        System.out.println("EMAIL: " + savedUser.getEmail());
        System.out.println("================================");

        return new UserDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }


    // =========================
    // LOGIN
    // =========================

    public String login(LoginRequest request) {

        User user =
                userRepository
                        .findByEmail(request.getEmail())
                        .orElseThrow(() ->
                                new InvalidCredentialsException(
                                        "Invalid email or password"
                                )
                        );

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!passwordMatches) {

            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        System.out.println("================================");
        System.out.println("LOGIN SUCCESSFUL");
        System.out.println("USER ID: " + user.getId());
        System.out.println("EMAIL: " + user.getEmail());
        System.out.println("================================");

        return jwtService.generateToken(
                user.getEmail()
        );
    }
}