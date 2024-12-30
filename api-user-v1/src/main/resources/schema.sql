CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    active BOOLEAN,
    created TIMESTAMP,
    modified TIMESTAMP,
    last_login TIMESTAMP,
    token VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS phones (
    phone_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(255),
    city_code VARCHAR(255),
    country_code VARCHAR(255),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS tokens (
    token_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255),
    user_id BIGINT,
    active BOOLEAN,
    created TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);