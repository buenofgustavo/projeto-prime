CREATE TABLE clientes (
                          id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
                          nome VARCHAR(255) NOT NULL,
                          categoria_id BIGINT NOT NULL,
                          responsavel VARCHAR(255) NOT NULL,
                          contato VARCHAR(255) NOT NULL,
                          cidade_id BIGINT NOT NULL,
                          data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);