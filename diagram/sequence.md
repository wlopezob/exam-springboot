sequenceDiagram
    actor User
    User->>+UserController: POST /user
    Note over User,UserController: UserRequest payload
    
    UserController->>+UserService: save(userRequest)
    UserService->>+UserRepository: findByEmailAndActive(email, true)
    UserRepository-->>-UserService: Mono<UserEntity>
    
    alt Email exists
        UserService-->>UserController: ApiException (ER0001)
        UserController-->>User: HTTP 409 Conflict
    else Email not found
        UserService->>UserService: Generate JWT token
        UserService->>+UserMapper: toEntity(userRequest, encodedPassword)
        UserMapper-->>-UserService: UserEntity
        UserService->>+UserRepository: save(userEntity)
        UserRepository-->>-UserService: Mono<UserEntity>
        
        UserService->>+PhoneMapper: toEntity(phoneRequest, userId)
        PhoneMapper-->>-UserService: PhoneEntity
        UserService->>+PhoneRepository: saveAll(phones)
        PhoneRepository-->>-UserService: Flux<PhoneEntity>
        
        UserService->>+TokenMapper: toEntity(token, userId)
        TokenMapper-->>-UserService: TokenEntity
        UserService->>+TokenRepository: save(tokenEntity)
        TokenRepository-->>-UserService: Mono<TokenEntity>
        
        UserService->>+UserMapper: toResponse(userEntity, token)
        UserMapper-->>-UserService: UserResponse
        
        UserService->>+RedisService: save(key, userEntity)
        RedisService-->>-UserService: Mono<UserEntity>
        
        UserService-->>-UserController: Mono<UserResponse>
        UserController-->>-User: HTTP 201 Created
    end