CREATE TABLE socios (
                        id CHAR(36) PRIMARY KEY,
                        nome VARCHAR(255),
                        cpf VARCHAR(14) NOT NULL,
                        email VARCHAR(255),
                        telefone VARCHAR(20),
                        percentual_participacao DOUBLE,
                        conta_pessoa_juridica_id CHAR(36) ,
                        FOREIGN KEY (conta_pessoa_juridica_id) REFERENCES contas_pj(id)
);