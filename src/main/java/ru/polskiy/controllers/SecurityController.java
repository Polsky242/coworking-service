package ru.polskiy.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.polskiy.dto.SecurityRequest;
import ru.polskiy.dto.TokenResponse;
import ru.polskiy.model.entity.User;
import ru.polskiy.service.SecurityService;

import java.util.List;

/**
 * Controller for managing security-related operations.
 * Provides endpoints for user registration and authentication.
 */
@RestController
@RequestMapping("/api/v1/security")
@Api(value = "Security Controller")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    /**
     * Registers a new user.
     *
     * @param request The request object containing user login and password.
     * @return ResponseEntity containing the registered User object.
     */
    @PostMapping("/sign-up")
    @ApiOperation(value = "Register a new user", response = User.class)
    public ResponseEntity<User> register(@RequestBody SecurityRequest request) {
        return ResponseEntity.ok(securityService.register(request.login(), request.password()));
    }

    /**
     * Authenticates a user and returns an authorization token.
     *
     * @param request The request object containing user login and password.
     * @return ResponseEntity containing the TokenResponse object with authorization token.
     */
    @PostMapping("/sign-in")
    @ApiOperation(value = "Authenticate user and return token", response = TokenResponse.class)
    public ResponseEntity<TokenResponse> authorize(@RequestBody SecurityRequest request) {
        return ResponseEntity.ok(securityService.authorize(request.login(), request.password()));
    }
}
