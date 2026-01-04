CREATE TABLE airports_distance (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    iata_code_origin CHAR(3) NOT NULL,
    iata_code_dest CHAR(3) NOT NULL,
    distance INT NOT NULL,
    FOREIGN KEY (iata_code_origin) REFERENCES airports(iata_code),
    FOREIGN KEY (iata_code_dest) REFERENCES airports(iata_code)
);