CREATE TABLE pessoa_juridica (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 cnpj VARCHAR(18) NOT NULL,
                                 logradouro VARCHAR(255),
                                 numero VARCHAR(10),
                                 complemento VARCHAR(255),
                                 bairro VARCHAR(255),
                                 cidade VARCHAR(255),
                                 estado VARCHAR(2),
                                 cep VARCHAR(10),
                                 pais VARCHAR(255),
                                 faturamento_mensal DOUBLE,
                                 agencia INT,
                                 numero_conta INT,
                                 status_solicitacao VARCHAR(20),
                                 UNIQUE(cnpj),
                                 senha VARCHAR(255),
                                 INDEX idx_status (status_solicitacao) USING BTREE
);