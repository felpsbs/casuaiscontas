CREATE TABLE grupo (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE permition (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE grupo_permition (
    id_grupo INT NOT NULL,
    id_permition INT NOT NULL,
    PRIMARY KEY (id_grupo, id_permition),
    FOREIGN KEY (id_grupo) REFERENCES grupo(id),
    FOREIGN KEY (id_permition) REFERENCES permition(id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;