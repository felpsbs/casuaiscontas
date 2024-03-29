CREATE TABLE user (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(80) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL, 
    password VARCHAR(120) NOT NULL,
    phone VARCHAR(20) NOT NULL DEFAULT '',
    cpf VARCHAR(30) NOT NULL DEFAULT '',
    birthdate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP, 
    updated_at DATETIME, 
    cep VARCHAR(15) NOT NULL DEFAULT '',
    id_city INT,
    FOREIGN KEY (id_city) REFERENCES city(id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE user_grupo (
    id_user INT NOT NULL,
    id_grupo INT NOT NULL,
    PRIMARY KEY (id_user, id_grupo),
    FOREIGN KEY (id_user) REFERENCES user(id),
    FOREIGN KEY (id_grupo) REFERENCES grupo(id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;