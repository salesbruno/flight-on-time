-- Índice composto para acelerar buscas por origem e destino
CREATE INDEX idx_airports_distance_origin_dest
ON airports_distance (iata_code_origin, iata_code_dest);