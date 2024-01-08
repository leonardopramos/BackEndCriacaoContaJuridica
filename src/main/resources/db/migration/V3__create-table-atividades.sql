CREATE TABLE atividades (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            code VARCHAR(20) NOT NULL,
                            text VARCHAR(255) NOT NULL,
                            tipo VARCHAR(255) NOT NULL,
                            conta_pessoa_juridica_id VARCHAR(36),
                            FOREIGN KEY (conta_pessoa_juridica_id) REFERENCES contas_pj(id)
);