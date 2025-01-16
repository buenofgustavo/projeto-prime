CREATE TABLE vendedores (
                          id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
                          nome VARCHAR(255) NOT NULL,
                          criado_por VARCHAR(255),
                          data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);