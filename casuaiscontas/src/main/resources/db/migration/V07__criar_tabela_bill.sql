CREATE TABLE bill (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    due_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, 
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP, 
    updated_at DATETIME,
    id_user INT NOT NULL,
    FOREIGN KEY (id_user) REFERENCES user(id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;
