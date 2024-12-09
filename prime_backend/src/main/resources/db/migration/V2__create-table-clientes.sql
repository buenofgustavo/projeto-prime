CREATE TABLE clientes (
                          id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
                          nome VARCHAR(255) NOT NULL,
                          categoria VARCHAR(255) NOT NULL,
                          responsavel VARCHAR(255) NOT NULL,
                          contato VARCHAR(255) NOT NULL,
                          cidade VARCHAR(255) NOT NULL,
                          data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);