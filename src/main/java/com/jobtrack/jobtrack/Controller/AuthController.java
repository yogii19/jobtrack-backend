package com.jobtrack.jobtrack.Controller;

import com.jobtrack.jobtrack.dto.LoginRequest;
import com.jobtrack.jobtrack.dto.RegisterRequest;
import com.jobtrack.jobtrack.dto.UserDTO;
import com.jobtrack.jobtrack.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(
                authService.register(request)
        );
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}