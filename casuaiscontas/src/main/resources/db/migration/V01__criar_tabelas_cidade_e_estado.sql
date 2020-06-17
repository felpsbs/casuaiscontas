CREATE TABLE state (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    uf VARCHAR(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE city (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    id_state INT NOT NULL,
    FOREIGN KEY (id_state) REFERENCES state(id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

INSERT INTO state (id, name, uf) VALUES (1,'Acre', 'AC');
INSERT INTO state (id, name, uf) VALUES (2,'Bahia', 'BA');
INSERT INTO state (id, name, uf) VALUES (3,'Goiás', 'GO');
INSERT INTO state (id, name, uf) VALUES (4,'Minas Gerais', 'MG');
INSERT INTO state (id, name, uf) VALUES (5,'Santa Catarina', 'SC');
INSERT INTO state (id, name, uf) VALUES (6,'São Paulo', 'SP');


INSERT INTO city (name, id_state) VALUES ('Rio Branco', 1);
INSERT INTO city (name, id_state) VALUES ('Cruzeiro do Sul', 1);
INSERT INTO city (name, id_state) VALUES ('Salvador', 2);
INSERT INTO city (name, id_state) VALUES ('Porto Seguro', 2);
INSERT INTO city (name, id_state) VALUES ('Santana', 2);
INSERT INTO city (name, id_state) VALUES ('Goiânia', 3);
INSERT INTO city (name, id_state) VALUES ('Itumbiara', 3);
INSERT INTO city (name, id_state) VALUES ('Novo Brasil', 3);
INSERT INTO city (name, id_state) VALUES ('Belo Horizonte', 4);
INSERT INTO city (name, id_state) VALUES ('Uberlândia', 4);
INSERT INTO city (name, id_state) VALUES ('Montes Claros', 4);
INSERT INTO city (name, id_state) VALUES ('Florianópolis', 5);
INSERT INTO city (name, id_state) VALUES ('Criciúma', 5);
INSERT INTO city (name, id_state) VALUES ('Camboriú', 5);
INSERT INTO city (name, id_state) VALUES ('Lages', 5);
INSERT INTO city (name, id_state) VALUES ('São Paulo', 6);
INSERT INTO city (name, id_state) VALUES ('Ribeirão Preto', 6);
INSERT INTO city (name, id_state) VALUES ('Campinas', 6);
INSERT INTO city (name, id_state) VALUES ('Santos', 6);
