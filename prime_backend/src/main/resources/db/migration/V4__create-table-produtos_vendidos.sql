CREATE TABLE produtos_vendidos (
                                   id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
                                   venda_id BIGINT NOT NULL,
                                   produto_id BIGINT NOT NULL,
                                   quantidade INTEGER NOT NULL,
                                   valor_unitario DOUBLE PRECISION NOT NULL,
                                   valor_total_produto DOUBLE PRECISION NOT NULL
                               );