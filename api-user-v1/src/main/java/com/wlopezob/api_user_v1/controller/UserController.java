package com.wlopezob.api_user_v1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wlopezob.api_user_v1.exception.DataApiException;
import com.wlopezob.api_user_v1.model.dto.UserRequest;
import com.wlopezob.api_user_v1.model.dto.UserResponse;
import com.wlopezob.api_user_v1.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * REST Controller for handling user-related operations.
 * This controller provides endpoints for managing user resources.
 * All endpoints are mapped under the "/user" base path.
 * @author wlopezob
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Creates a new user based on the provided user request.
     *
     * @param userRequest The user data transfer object containing the user
     *         information to be saved
     * @return A Mono wrapping a ResponseEntity containing the created UserResponse
     *         with HTTP status 201 (CREATED)
     */
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided information including name, email, and phone numbers", responses = {
            @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataApiException.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataApiException.class))),
            @ApiResponse(responseCode = "409", description = "Error validation email", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataApiException.class)))
    })
    @PostMapping
    public Mono<ResponseEntity<UserResponse>> save(@RequestBody @Valid UserRequest userRequest) {
        return userService.save(userRequest)
                .map(response -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(response));
    }
}
