classDiagram
    class UserController {
        -UserService userService
        +save(UserRequest) Mono~ResponseEntity~UserResponse~~
    }

    class UserService {
        <<interface>>
        +save(UserRequest) Mono~UserResponse~
    }

    class UserServiceImpl {
        -UserRepository userRepository
        -PhoneRepository phoneRepository
        -TokenRepository tokenRepository
        -RedisService redisService
        -UserMapper userMapper
        -PhoneMapper phoneMapper
        -TokenMapper tokenMapper
        -JwtToken jwtToken
        -PasswordEncoder passwordEncoder
        +save(UserRequest) Mono~UserResponse~
        -validateEmail(UserRequest) Mono~Void~
    }

    class CustomControllerAdvice {
        +handlerApiException(ApiException) Mono~ResponseEntity~DataApiException~~
        +handlerApiException(Exception) Mono~ResponseEntity~DataApiException~~
        +handleValidationExceptions(WebExchangeBindException) Mono~ResponseEntity~DataApiException~~
    }

    class RedisService {
        <<interface>>
        +save(String, T) Mono~T~
    }

    class RedisServiceImpl {
        -ReactiveRedisTemplate redisTemplate
        +save(String, T) Mono~T~
    }

    class UserEntity {
        -Long userId
        -String name
        -String email
        -String password
        -boolean active
        -LocalDateTime created
        -LocalDateTime modified
        -LocalDateTime lastLogin
    }

    class PhoneEntity {
        -Long phoneId
        -String number
        -String cityCode
        -String countryCode
        -Long userId
    }

    class TokenEntity {
        -Long tokenId
        -String token
        -Long userId
        -boolean active
        -LocalDateTime created
    }

    class UserRequest {
        -String name
        -String email
        -String password
        -List~PhoneRequest~ phones
    }

    class PhoneRequest {
        -String number
        -String cityCode
        -String countryCode
    }

    class UserResponse {
        -String userId
        -String name
        -String email
        -boolean active
        -String created
        -String modified
        -String lastLogin
        -String token
    }

    class ApiException {
        -String mensaje
        -int httpStatus
        -Throwable cause
    }

    class DataApiException {
        -String mensaje
        -int httpStatus
    }

    class UserMemory {
        -UserEntity userEntity
        -UserResponse userResponse
    }

    class ValidationConfig {
        -PasswordValidation password
    }

    class PasswordValidator {
        -ValidationConfig validationConfig
        -Pattern pattern
        +isValid(String, ConstraintValidatorContext) boolean
    }

    class JwtToken {
        -String secret
        -long expiration
        -String rol
        +generateToken(UserRequest) String
    }

    class SecurityConfig {
        +filterChain(ServerHttpSecurity) SecurityWebFilterChain
    }

    %% Repositories
    class UserRepository {
        <<interface>>
        +findByEmailAndActive(String, boolean) Mono~UserEntity~
    }

    class PhoneRepository {
        <<interface>>
    }

    class TokenRepository {
        <<interface>>
    }

    %% Mappers
    class UserMapper {
        <<interface>>
        +toEntity(UserRequest, String) UserEntity
        +toResponse(UserEntity, String) UserResponse
    }

    class PhoneMapper {
        <<interface>>
        +toEntity(PhoneRequest, Long) PhoneEntity
    }

    class TokenMapper {
        <<interface>>
        +toEntity(String, Long) TokenEntity
    }

    %% Relationships
    UserController --> UserService
    UserService <|.. UserServiceImpl
    UserServiceImpl --> UserRepository
    UserServiceImpl --> PhoneRepository
    UserServiceImpl --> TokenRepository
    UserServiceImpl --> RedisService
    UserServiceImpl --> UserMapper
    UserServiceImpl --> PhoneMapper
    UserServiceImpl --> TokenMapper
    UserServiceImpl --> JwtToken
    RedisService <|.. RedisServiceImpl
    UserRequest --> PhoneRequest
    UserMapper --> UserEntity
    UserMapper --> UserResponse
    PhoneMapper --> PhoneEntity
    TokenMapper --> TokenEntity
    UserMemory --> UserEntity
    UserMemory --> UserResponse
    PasswordValidator --> ValidationConfig
    CustomControllerAdvice --> DataApiException
    CustomControllerAdvice --> ApiException