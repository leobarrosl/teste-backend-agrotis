-- LABORATÓRIOS
INSERT INTO laboratorios (nome) VALUES ('Laboratório Modelo 1');
INSERT INTO laboratorios (nome) VALUES ('Laboratório Modelo 2');
INSERT INTO laboratorios (nome) VALUES ('Laboratório Modelo 3');
INSERT INTO laboratorios (nome) VALUES ('Laboratório Modelo 4');

-- PROPRIEDADES
INSERT INTO propriedades (nome) VALUES ('Fazenda Modelo 1');
INSERT INTO propriedades (nome) VALUES ('Fazenda Modelo 2');
INSERT INTO propriedades (nome) VALUES ('Fazenda Modelo 3');
INSERT INTO propriedades (nome) VALUES ('Fazenda Modelo 4');
INSERT INTO propriedades (nome) VALUES ('Fazenda Modelo 5');

-- PESSOAS
INSERT INTO pessoas (nome, data_inicial, data_final, observacoes, propriedade_id, laboratorio_id)
VALUES ('Carlos Silva', '2024-01-10T08:00:00', '2024-01-20T17:00:00', 'Teste 1', 1, 1);

INSERT INTO pessoas (nome, data_inicial, data_final, observacoes, propriedade_id, laboratorio_id)
VALUES ('Mariana Souza', '2025-02-01T09:00:00', '2024-02-15T16:30:00', 'Teste 2', 2, 2);

INSERT INTO pessoas (nome, data_inicial, data_final, observacoes, propriedade_id, laboratorio_id)
VALUES ('João Mendes', '2024-03-05T07:30:00', '2024-03-20T17:15:00', 'Teste 3', 3, 3);

INSERT INTO pessoas (nome, data_inicial, data_final, observacoes, propriedade_id, laboratorio_id)
VALUES ('Luciana Dias', '2024-04-10T10:00:00', '2024-04-18T16:00:00', 'Teste 4', 4, 4);

INSERT INTO pessoas (nome, data_inicial, data_final, observacoes, propriedade_id, laboratorio_id)
VALUES ('Fernando Lima', '2024-05-01T08:15:00', '2024-05-10T15:45:00', 'Diferente', 1, 2);

INSERT INTO pessoas (nome, data_inicial, data_final, observacoes, propriedade_id, laboratorio_id)
VALUES ('Aline Martins', '2024-06-12T09:30:00', '2024-06-22T14:00:00', 'Teste 6', 2, 3);

INSERT INTO pessoas (nome, data_inicial, data_final, observacoes, propriedade_id, laboratorio_id)
VALUES ('Ricardo Gomes', '2024-07-03T07:45:00', '2024-07-12T17:30:00', 'Teste 7', 3, 4);

INSERT INTO pessoas (nome, data_inicial, data_final, observacoes, propriedade_id, laboratorio_id)
VALUES ('Patrícia Alves', '2024-08-14T10:20:00', '2024-08-25T15:00:00', 'Teste 8', 4, 1);

INSERT INTO pessoas (nome, data_inicial, data_final, observacoes, propriedade_id, laboratorio_id)
VALUES ('Roberto Nunes', '2024-09-01T08:00:00', '2024-09-10T16:00:00', 'Teste 9', 1, 3);

INSERT INTO pessoas (nome, data_inicial, data_final, observacoes, propriedade_id, laboratorio_id)
VALUES ('Vanessa Rocha', '2024-10-05T09:00:00', '2024-10-15T15:30:00', 'Teste 10', 2, 4);

INSERT INTO pessoas (nome, data_inicial, data_final, observacoes, propriedade_id, laboratorio_id)
VALUES ('Vanessa Rocha', '2025-10-05T09:00:00', '2023-10-15T15:30:00', 'Teste 10', 2, 2);
