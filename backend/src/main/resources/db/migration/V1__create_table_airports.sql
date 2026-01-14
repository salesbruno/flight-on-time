CREATE TABLE airports (
    iata_code VARCHAR(10) PRIMARY KEY,
    airport_name VARCHAR(255) NOT NULL,
    city_name VARCHAR(255),
    state_name VARCHAR(255),
    country_name VARCHAR(255),
    latitude NUMERIC(9,6),
    longitude NUMERIC(9,6),
    timezone VARCHAR(50),
    active BOOLEAN DEFAULT TRUE
);