package com.nataliia.koval.movieland.web.controller;

import com.nataliia.koval.movieland.service.SecurityTokenService;
import com.nataliia.koval.movieland.service.UserService;
import com.nataliia.koval.movieland.web.controller.entity.LoginRequest;
import com.nataliia.koval.movieland.web.controller.entity.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {
    private final UserService userService;
    private final SecurityTokenService securityTokenService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.email();
        String password = loginRequest.password();

        if (userService.isInvalidUser(email, password)) {
            return ResponseEntity.badRequest().body("Invalid user details. Check email and password");
        }

        String token = securityTokenService.generateAndStoreTokenInCache(email);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate token.");
        }

        String nickname = userService.getUserNickname(email);
        return ResponseEntity.ok(new LoginResponse(token, nickname));
    }

    @DeleteMapping("/logout/{uuid}")
    public ResponseEntity<?> logout(@PathVariable String uuid) {
        if (uuid == null || uuid.isEmpty()) {
            return ResponseEntity.badRequest().body("UUID is required in the request header.");
        }

        if (securityTokenService.isTokenInvalid(uuid)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }

        securityTokenService.invalidateToken(uuid);
        return ResponseEntity.ok().build();
    }
}
