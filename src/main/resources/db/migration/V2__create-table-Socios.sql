CREATE TABLE socios (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       nome VARCHAR(255),
                       cpf VARCHAR(14) NOT NULL,
                       email VARCHAR(255),
                       percentual_participacao DOUBLE,
                       conta_pessoa_juridica_id BIGINT,
                       FOREIGN KEY (conta_pessoa_juridica_id) REFERENCES pessoa_juridica(id)
);