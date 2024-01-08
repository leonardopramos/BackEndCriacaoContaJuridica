CREATE TABLE socios (
                        id CHAR(36) PRIMARY KEY,
                        nome VARCHAR(255) NOT NULL,
                        cpf VARCHAR(14) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        telefone VARCHAR(20) NOT NULL,
                        percentual_participacao DOUBLE NOT NULL,
                        conta_pessoa_juridica_id VARCHAR(36),
                        FOREIGN KEY (conta_pessoa_juridica_id) REFERENCES contas_pj(id)
);