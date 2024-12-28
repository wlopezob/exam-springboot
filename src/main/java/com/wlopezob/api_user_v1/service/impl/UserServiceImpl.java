package com.wlopezob.api_user_v1.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wlopezob.api_user_v1.mapper.UserMapper;
import com.wlopezob.api_user_v1.model.dto.UserMemory;
import com.wlopezob.api_user_v1.model.dto.UserRequest;
import com.wlopezob.api_user_v1.model.dto.UserResponse;
import com.wlopezob.api_user_v1.repository.UserRepository;
import com.wlopezob.api_user_v1.repository.PhoneRepository;
import com.wlopezob.api_user_v1.repository.TokenRepository;
import com.wlopezob.api_user_v1.service.RedisService;
import com.wlopezob.api_user_v1.service.UserService;
import com.wlopezob.api_user_v1.util.Util;
import com.wlopezob.api_user_v1.config.JwtToken;
import com.wlopezob.api_user_v1.exception.ApiException;
import com.wlopezob.api_user_v1.exception.ApiExceptionEnum;
import com.wlopezob.api_user_v1.mapper.PhoneMapper;
import com.wlopezob.api_user_v1.mapper.TokenMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service implementation class for managing user-related operations.
 * This service handles user registration, validation, and token management.
 * @author wlopezob
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PhoneRepository phoneRepository;
	private final UserMapper userMapper;
	private final PhoneMapper phoneMapper;
	private final PasswordEncoder passwordEncoder;
	private final JwtToken jwtToken;
	private final TokenRepository tokenRepository;
	private final TokenMapper tokenMapper;
	private final RedisService redisService;

	/**
	 * Saves a new user with their associated phone numbers and authentication token.
	 * The process includes:
	 * 1. Email validation
	 * 2. User entity creation with encoded password
	 * 3. Phone numbers registration
	 * 4. JWT token generation and storage
	 * 5. User data caching in Redis
	 *
	 * @param userRequest The user information to be saved containing user details and phone numbers
	 * @return Mono<UserResponse> containing the saved user information with generated token and UUID
	 * @throws ApiException if the email is already registered
	 * @throws ApiException if there's an error during the save process
	 */
	@Override
	@Transactional
	public Mono<UserResponse> save(UserRequest userRequest) {
		String token = jwtToken.generateToken(userRequest);
		return validateEmail(userRequest).then(Mono
				.just(userMapper.toEntity(userRequest, passwordEncoder
						.encode(userRequest.getPassword())))
				// register user
				.flatMap(userEntity -> userRepository.save(userEntity)
						.map(savedUser -> userRequest.getPhones().stream()
								.filter(Objects::nonNull)
								.map(phone -> phoneMapper.toEntity(phone,
										savedUser.getUserId()))
								.collect(Collectors.toList()))
						// register phones
						.flatMap(phones -> phoneRepository.saveAll(phones)
						// register token
								.then(tokenRepository
										.save(tokenMapper.toEntity(token, userEntity.getUserId()))))
						.map(savedToken -> userMapper.toResponse(userEntity, savedToken.getToken()))
						.map(userResponse -> UserMemory.builder().userEntity(userEntity)
								.userResponse(userResponse).build())))
				.flatMap(userMemory -> {
					// register user in Redis with uuid
					String uuid = UUID.randomUUID().toString();
					userMemory.getUserResponse().setUserId(uuid);
					return redisService.save(Util.getKeyRedis(uuid), userMemory.getUserEntity())
							.thenReturn(userMemory.getUserResponse());
				});
	}

	/**
	 * Validates if a user with the given email already exists and is active.
	 * 
	 * @param userRequest The user request containing the email to validate
	 * @return A Mono<Void> that completes successfully if email is valid,
	 *         or errors with ApiExceptionEnum.ER0001 if email already exists
	 * @throws ApiException when email already exists and is active
	 */
	private Mono<Void> validateEmail(UserRequest userRequest) {
		return userRepository.findByEmailAndActive(userRequest.getEmail(), true)
				.flatMap(user -> Mono.error(ApiExceptionEnum.ER0001.buildException()))
				.then();
	}

}