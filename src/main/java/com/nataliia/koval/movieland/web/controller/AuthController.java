package com.nataliia.koval.movieland.web.controller;

import com.nataliia.koval.movieland.exception.AuthException;
import com.nataliia.koval.movieland.service.security.JwtSecurityTokenService;
import com.nataliia.koval.movieland.service.UserService;
import com.nataliia.koval.movieland.web.controller.entity.LoginRequest;
import com.nataliia.koval.movieland.web.controller.entity.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {
    private final UserService userService;
    private final JwtSecurityTokenService securityTokenService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.email();
        String password = loginRequest.password();

        if (userService.isInvalidUser(email, password)) {
            throw new AuthException("Wrong combination of login/password. Check the request details please!");
        }

        String uuid = securityTokenService.generateAndStoreTokenInCache(email);

        String nickname = userService.getUserNickname(email);
        MDC.put("userIdentifier", email);
        return ResponseEntity.ok(new LoginResponse(uuid, nickname));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("uuid") String uuid) {
        if (uuid == null || uuid.isEmpty()) {
            throw new AuthException("UUID is required in the request header.");
        }

        if (securityTokenService.isTokenInvalid(uuid)) {
            throw new AuthException("Invalid token.");
        }

        String logoutMessage = securityTokenService.invalidateToken(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(logoutMessage);
    }
}
