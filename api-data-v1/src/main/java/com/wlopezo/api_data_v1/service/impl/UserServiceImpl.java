package com.wlopezo.api_data_v1.service.impl;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wlopezo.api_data_v1.exception.ApiException;
import com.wlopezo.api_data_v1.exception.ApiExceptionEnum;
import com.wlopezo.api_data_v1.mapper.PhoneMapper;
import com.wlopezo.api_data_v1.mapper.TokenMapper;
import com.wlopezo.api_data_v1.mapper.UserMapper;
import com.wlopezo.api_data_v1.model.dto.UserDataRequest;
import com.wlopezo.api_data_v1.model.dto.UserDataResponse;
import com.wlopezo.api_data_v1.repository.PhoneRepository;
import com.wlopezo.api_data_v1.repository.TokenRepository;
import com.wlopezo.api_data_v1.repository.UserRepository;
import com.wlopezo.api_data_v1.service.UserService;

import jakarta.transaction.Transactional;
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
	private final UserRepository userRepository;
	private final PhoneRepository phoneRepository;
	private final UserMapper userMapper;
	private final PhoneMapper phoneMapper;
	private final TokenRepository tokenRepository;
	private final TokenMapper tokenMapper;

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
	@Transactional
	public Mono<UserDataResponse> save(UserDataRequest userRequest) {
		String token = userRequest.getToken();
		return validateEmail(userRequest).then(Mono
				.just(userMapper.toEntity(userRequest))
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
						.map(savedToken -> userMapper.toResponse(userEntity, savedToken.getToken()))));
	}

	/**
	 * Validates if a user with the given email already exists and is active.
	 * 
	 * @param userRequest The user request containing the email to validate
	 * @return A Mono<Void> that completes successfully if email is valid,
	 *         or errors with ApiExceptionEnum.ER0001 if email already exists
	 * @throws ApiException when email already exists and is active
	 */
	private Mono<Void> validateEmail(UserDataRequest userRequest) {
		return userRepository.findByEmailAndActive(userRequest.getEmail(), true)
				.flatMap(user -> Mono.error(ApiExceptionEnum.ER0001.buildException()))
				.then();
	}

}