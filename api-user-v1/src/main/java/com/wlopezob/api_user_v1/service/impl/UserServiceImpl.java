package com.wlopezob.api_user_v1.service.impl;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wlopezob.api_user_v1.apicaller.ApiDataCaller;
import com.wlopezob.api_user_v1.config.JwtToken;
import com.wlopezob.api_user_v1.exception.ApiException;
import com.wlopezob.api_user_v1.mapper.UserMapper;
import com.wlopezob.api_user_v1.model.dto.UserMemory;
import com.wlopezob.api_user_v1.model.dto.UserRequest;
import com.wlopezob.api_user_v1.model.dto.UserResponse;
import com.wlopezob.api_user_v1.service.RedisService;
import com.wlopezob.api_user_v1.service.UserService;
import com.wlopezob.api_user_v1.util.Util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Service implementation class for managing user-related operations.
 * This service handles user registration, validation, and token management.
 * 
 * @author wlopezob
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final JwtToken jwtToken;
	private final RedisService redisService;
	private final ApiDataCaller apiDataCaller;

	/**
	 * Saves a new user with their associated phone numbers and authentication
	 * token.
	 * The process includes:
	 * 1. Email validation
	 * 2. User entity creation with encoded password
	 * 3. Phone numbers registration
	 * 4. JWT token generation and storage
	 * 5. User data caching in Redis
	 *
	 * @param userRequest The user information to be saved containing user details
	 *                    and phone numbers
	 * @return Mono<UserResponse> containing the saved user information with
	 *         generated token and UUID
	 * @throws ApiException if the email is already registered
	 * @throws ApiException if there's an error during the save process
	 */
	@Override
	public Mono<UserResponse> save(UserRequest userRequest) {
		String token = jwtToken.generateToken(userRequest);
		String password = passwordEncoder.encode(userRequest.getPassword());
		return Mono
				.just(userMapper.toUserDataRequest(userRequest, password, token))
				// register user
				.flatMap(userDataRequest -> apiDataCaller.save(userDataRequest)
						.map(userDataResponse -> UserMemory.builder()
								.userDataResponse(userDataResponse)
								.userResponse(userMapper.toUserResponse(userDataResponse))
								.build())
						.flatMap(userMemory -> {
							// register user in Redis with uuid
							String uuid = UUID.randomUUID().toString();
							userMemory.getUserResponse().setUserId(uuid);
							return redisService.save(Util.getKeyRedis(uuid), userMemory.getUserDataResponse())
									.thenReturn(userMemory.getUserResponse());
						}));
	}

}