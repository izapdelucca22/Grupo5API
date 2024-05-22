-- Criação da tabela Usuário
CREATE TABLE Usuario (
    id SERIAL PRIMARY KEY,
    primeiro_nome VARCHAR(50) NOT NULL,
    ultimo_nome VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    data_nascimento DATE NOT NULL
);

-- Criação da tabela Postagem
CREATE TABLE Postagem (
    id SERIAL PRIMARY KEY,
    conteudo TEXT NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
);

-- Criação da tabela Relacionamento (Seguindo)
CREATE TABLE Relacionamento (
    id SERIAL PRIMARY KEY,
    usuario_seguidor_id INT NOT NULL,
    usuario_seguido_id INT NOT NULL,
    FOREIGN KEY (usuario_seguidor_id) REFERENCES Usuario(id),
    FOREIGN KEY (usuario_seguido_id) REFERENCES Usuario(id),
    UNIQUE (usuario_seguidor_id, usuario_seguido_id)
);

-- Criação da tabela Comentário
CREATE TABLE Comentario (
    id SERIAL PRIMARY KEY,
    conteudo TEXT NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    postagem_id INT NOT NULL,
    usuario_id INT NOT NULL,
    FOREIGN KEY (postagem_id) REFERENCES Postagem(id),
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
);

-- Criação da tabela Role
CREATE TABLE Role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

-- Criação da tabela intermediária usuario_roles
CREATE TABLE usuario_roles (
    usuario_id INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
    FOREIGN KEY (role_id) REFERENCES Role(id),
    PRIMARY KEY (usuario_id, role_id)
);