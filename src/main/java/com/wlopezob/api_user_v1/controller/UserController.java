package com.wlopezob.api_user_v1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wlopezob.api_user_v1.model.dto.UserRequest;
import com.wlopezob.api_user_v1.model.dto.UserResponse;
import com.wlopezob.api_user_v1.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public Mono<ResponseEntity<UserResponse>> save(@RequestBody @Valid UserRequest userRequest) {
        return userService.save(userRequest)
            .map(response -> ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response));
    }
}
