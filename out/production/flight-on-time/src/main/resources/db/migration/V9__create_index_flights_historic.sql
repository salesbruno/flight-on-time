-- Índice para consultas por companhia em períodos
CREATE INDEX idx_flights_historic_companhia_data
    ON flights_historic (companhia, data_partida);

-- Índice para consultas por rotas (origem + destino)
CREATE INDEX idx_flights_historic_origem_destino
    ON flights_historic (origem, destino);

-- Índice para consultas por intervalo de tempo
CREATE INDEX idx_flights_historic_data_partida
    ON flights_historic (data_partida);

-- Índice para consultas por estado de origem
CREATE INDEX idx_flights_historic_estado_origem
    ON flights_historic (estado_origem);

-- Índice para consultas por estado de destino
CREATE INDEX idx_flights_historic_estado_destino
    ON flights_historic (estado_destino);