-- Apaga o banco de dados antigo, se ele existir
DROP DATABASE IF EXISTS gerenciamento_locadora;

-- Cria o novo banco de dados
CREATE DATABASE gerenciamento_locadora;

-- Seleciona o banco para usar
USE gerenciamento_locadora;

-- Tabela de filmes (Sem mudanças)
CREATE TABLE filmes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    ano_lancamento INT NOT NULL,
    categoria VARCHAR(100),
    duracao INT,
    vencedor_oscar BOOLEAN,
    direcao VARCHAR(100),
    elenco_principal TEXT,
    copias_disponiveis INT CHECK (copias_disponiveis BETWEEN 1 AND 6)
);
-- ... (Todos os seus INSERTS de FILMES aqui - eles estão corretos) ...
INSERT INTO filmes (nome, ano_lancamento, categoria, duracao, vencedor_oscar, direcao, elenco_principal, copias_disponiveis) VALUES
('Titanic', 1997, 'Drama/Romance', 195, TRUE, 'James Cameron', 'Leonardo DiCaprio, Kate Winslet', 4),
('O Senhor dos Anéis: O Retorno do Rei', 2003, 'Fantasia/Aventura', 201, TRUE, 'Peter Jackson', 'Elijah Wood, Viggo Mortensen', 2),
('Forrest Gump', 1994, 'Drama/Romance', 142, TRUE, 'Robert Zemeckis', 'Tom Hanks, Robin Wright', 5),
('Gladiador', 2000, 'Ação/Drama', 155, TRUE, 'Ridley Scott', 'Russell Crowe, Joaquin Phoenix', 3),
('A Origem', 2010, 'Ficção Científica/Ação', 148, FALSE, 'Christopher Nolan', 'Leonardo DiCaprio, Joseph Gordon-Levitt', 6),
('O Poderoso Chefão', 1972, 'Crime/Drama', 175, TRUE, 'Francis Ford Coppola', 'Marlon Brando, Al Pacino', 1),
('Clube da Luta', 1999, 'Drama', 139, FALSE, 'David Fincher', 'Brad Pitt, Edward Norton', 4),
('Coringa', 2019, 'Drama/Crime', 122, TRUE, 'Todd Phillips', 'Joaquin Phoenix, Robert De Niro', 2),
('Parasita', 2019, 'Suspense/Drama', 132, TRUE, 'Bong Joon-ho', 'Song Kang-ho, Lee Sun-kyun', 5),
('Matrix', 1999, 'Ficção Científica/Ação', 136, TRUE, 'Lana Wachowski, Lilly Wachowski', 'Keanu Reeves, Laurence Fishburne', 3),
('O Rei Leão', 1994, 'Animação/Aventura', 88, TRUE, 'Roger Allers, Rob Minkoff', 'Matthew Broderick, Jeremy Irons', 6),
('Interestelar', 2014, 'Ficção Científica/Drama', 169, FALSE, 'Christopher Nolan', 'Matthew McConaughey, Anne Hathaway', 1),
('O Grande Gatsby', 2013, 'Drama/Romance', 143, TRUE, 'Baz Luhrmann', 'Leonardo DiCaprio, Carey Mulligan', 2),
('Bohemian Rhapsody', 2018, 'Biografia/Música', 134, TRUE, 'Bryan Singer', 'Rami Malek, Lucy Boynton', 4),
('O Discurso do Rei', 2010, 'Drama/Histórico', 118, TRUE, 'Tom Hooper', 'Colin Firth, Geoffrey Rush', 3),
('La La Land', 2016, 'Musical/Romance', 128, TRUE, 'Damien Chazelle', 'Ryan Gosling, Emma Stone', 5),
('O Lado Bom da Vida', 2012, 'Drama/Romance', 122, FALSE, 'David O. Russell', 'Bradley Cooper, Jennifer Lawrence', 2),
('O Código Da Vinci', 2006, 'Suspense/Mistério', 149, FALSE, 'Ron Howard', 'Tom Hanks, Audrey Tautou', 6),
('Os Infiltrados', 2006, 'Crime/Suspense', 151, TRUE, 'Martin Scorsese', 'Leonardo DiCaprio, Matt Damon', 1),
('O Pianista', 2002, 'Drama/Guerra', 150, TRUE, 'Roman Polanski', 'Adrien Brody, Thomas Kretschmann', 3);


-- =======================================================
-- Tabela de Clientes (CORRIGIDA)
-- Adicionamos as colunas 'senha', 'ativo', e 'role'
-- =======================================================
CREATE TABLE clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,        -- Para o hash BCrypt
    ativo BOOLEAN DEFAULT true,         -- Para o Soft Delete
    role VARCHAR(20) NOT NULL,          -- Para o Controle de Acesso
    telefone VARCHAR(20),
    
    -- Campos de Endereço
    cep VARCHAR(10) NOT NULL,
    logradouro VARCHAR(100) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    complemento VARCHAR(100),
    bairro VARCHAR(50) NOT NULL,
    cidade VARCHAR(50) NOT NULL,
    estado VARCHAR(2) NOT NULL
);

-- =======================================================
-- INSERTS de Clientes (CORRIGIDOS)
-- Adicionamos a senha 'DISABLED' e o 'role' como 'ROLE_CLIENTE'
-- =======================================================
INSERT INTO clientes (nome, telefone, email, cpf, cep, logradouro, numero, complemento, bairro, cidade, estado, senha, ativo, role) VALUES
('Ana Beatriz', '11999990001', 'anabeatriz@email.com', '00000000001', '01001000', 'Rua das Flores', '123', NULL, 'Centro', 'São Paulo', 'SP', 'DISABLED', true, 'ROLE_CLIENTE'),
('Carlos Eduardo', '11999990002', 'carloseduardo@email.com', '00000000002', '04543000', 'Av. Brasil', '456', NULL, 'Vila Olímpia', 'São Paulo', 'SP', 'DISABLED', true, 'ROLE_CLIENTE'),
('Fernanda Lima', '11999990003', 'fernandalima@email.com', '00000000003', '70002900', 'Rua A', '789', 'Apto 101', 'Asa Sul', 'Brasília', 'DF', 'DISABLED', true, 'ROLE_CLIENTE'),
('João Pedro', '11999990004', 'joaopedro@email.com', '00000000004', '20090000', 'Rua B', '321', NULL, 'Centro', 'Rio de Janeiro', 'RJ', 'DISABLED', true, 'ROLE_CLIENTE'),
('Mariana Silva', '11999990005', 'marianasilva@email.com', '00000000005', '90010150', 'Rua C', '654', NULL, 'Centro Histórico', 'Porto Alegre', 'RS', 'DISABLED', true, 'ROLE_CLIENTE'),
('Lucas Rocha', '11999990006', 'lucasrocha@email.com', '00000000006', '30130000', 'Rua D', '987', NULL, 'Funcionários', 'Belo Horizonte', 'MG', 'DISABLED', true, 'ROLE_CLIENTE'),
('Juliana Costa', '11999990007', 'julianacosta@email.com', '00000000007', '80010000', 'Rua E', '111', 'Casa 2', 'Centro', 'Curitiba', 'PR', 'DISABLED', true, 'ROLE_CLIENTE'),
('Rafael Souza', '11999990008', 'rafaelsouza@email.com', '00000000008', '40010000', 'Rua F', '222', NULL, 'Comércio', 'Salvador', 'BA', 'DISABLED', true, 'ROLE_CLIENTE'),
('Patrícia Mendes', '11999990009', 'patriciamendes@email.com', '00000000009', '60010000', 'Rua G', '333', NULL, 'Centro', 'Fortaleza', 'CE', 'DISABLED', true, 'ROLE_CLIENTE'),
('Bruno Oliveira', '11999990010', 'brunoolira@email.com', '00000000010', '50010000', 'Rua H', '444', NULL, 'Santo Antônio', 'Recife', 'PE', 'DISABLED', true, 'ROLE_CLIENTE'),
('Camila Torres', '11999990011', 'camilatorres@email.com', '00000000011', '69005000', 'Rua I', '555', NULL, 'Centro', 'Manaus', 'AM', 'DISABLED', true, 'ROLE_CLIENTE'),
('Thiago Martins', '11999990012', 'thiagomartins@email.com', '00000000012', '74001000', 'Rua J', '666', NULL, 'Setor Central', 'Goiânia', 'GO', 'DISABLED', true, 'ROLE_CLIENTE'),
('Isabela Ferreira', '11999990013', 'isabelaferreira@email.com', '00000000013', '59002000', 'Rua K', '777', NULL, 'Ribeira', 'Natal', 'RN', 'DISABLED', true, 'ROLE_CLIENTE'),
('Gustavo Lima', '11999990014', 'gustavolima@email.com', '00000000014', '66010000', 'Rua L', '888', NULL, 'Campina', 'Belém', 'PA', 'DISABLED', true, 'ROLE_CLIENTE'),
('Larissa Alves', '11999990015', 'larissaalves@email.com', '00000000015', '88010000', 'Rua M', '999', NULL, 'Centro', 'Florianópolis', 'SC', 'DISABLED', true, 'ROLE_CLIENTE'),
('Eduardo Ramos', '11999990016', 'eduardoramos@email.com', '00000000016', '13010000', 'Rua N', '101', NULL, 'Centro', 'Campinas', 'SP', 'DISABLED', true, 'ROLE_CLIENTE'),
('Natália Duarte', '11999990017', 'nataliaduarte@email.com', '00000000017', '95010000', 'Rua O', '202', NULL, 'Centro', 'Caxias do Sul', 'RS', 'DISABLED', true, 'ROLE_CLIENTE'),
('Felipe Barbosa', '11999990018', 'felipebarbosa@email.com', '00000000018', '38400000', 'Rua P', '303', NULL, 'Centro', 'Uberlândia', 'MG', 'DISABLED', true, 'ROLE_CLIENTE'),
('Vanessa Cardoso', '11999990019', 'vanessacardoso@email.com', '00000000019', '14010000', 'Rua Q', '404', NULL, 'Centro', 'Ribeirão Preto', 'SP', 'DISABLED', true, 'ROLE_CLIENTE'),
('Rodrigo Cunha', '11999990020', 'rodrigocunha@email.com', '00000000020', '65010000', 'Rua R', '505', NULL, 'Centro', 'São Luís', 'MA', 'DISABLED', true, 'ROLE_CLIENTE'),
('Tatiane Gomes', '11999990021', 'tatianegomes@email.com', '00000000021', '79002000', 'Rua S', '606', NULL, 'Centro', 'Campo Grande', 'MS', 'DISABLED', true, 'ROLE_CLIENTE'),
('André Nunes', '11999990022', 'andrenunes@email.com', '00000000022', '69301000', 'Rua T', '707', NULL, 'Centro', 'Boa Vista', 'RR', 'DISABLED', true, 'ROLE_CLIENTE'),
('Débora Pires', '11999990023', 'deborapires@email.com', '00000000023', '76801000', 'Rua U', '808', NULL, 'Centro', 'Porto Velho', 'RO', 'DISABLED', true, 'ROLE_CLIENTE'),
('Henrique Teixeira', '11999990024', 'henriqueteixeira@email.com', '00000000024', '49001000', 'Rua V', '909', NULL, 'Centro', 'Aracaju', 'SE', 'DISABLED', true, 'ROLE_CLIENTE'),
('Sabrina Lopes', '11999990025', 'sabrinalopes@email.com', '00000000025', '57001000', 'Rua W', '121', NULL, 'Centro', 'Maceió', 'AL', 'DISABLED', true, 'ROLE_CLIENTE'),
('Marcelo Tavares', '11999990026', 'marcelotavares@email.com', '00000000026', '29010000', 'Rua X', '131', NULL, 'Centro', 'Vitória', 'ES', 'DISABLED', true, 'ROLE_CLIENTE'),
('Aline Ribeiro', '11999990027', 'alineribeiro@email.com', '00000000027', '97001000', 'Rua Y', '141', NULL, 'Centro', 'Santa Maria', 'RS', 'DISABLED', true, 'ROLE_CLIENTE'),
('Renato Almeida', '11999990028', 'renatoalmeida@email.com', '00000000028', '64001000', 'Rua Z', '151', NULL, 'Centro', 'Teresina', 'PI', 'DISABLED', true, 'ROLE_CLIENTE'),
('Beatriz Cunha', '11999990029', 'beatrizcunha@email.com', '00000000029', '68900000', 'Rua AA', '161', NULL, 'Centro', 'Macapá', 'AP', 'DISABLED', true, 'ROLE_CLIENTE'),
('Fábio Moreira', '11999990030', 'fabiomoreira@email.com', '00000000030', '77001000', 'Rua BB', '171', NULL, 'Centro', 'Palmas', 'TO', 'DISABLED', true, 'ROLE_CLIENTE');


-- Tabela de Locações (Sem mudanças, mas aqui para completar)
CREATE TABLE locacoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cliente BIGINT NOT NULL,
    id_filme BIGINT NOT NULL,
    data_locacao DATE NOT NULL,
    data_devolucao DATE,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id),
    FOREIGN KEY (id_filme) REFERENCES filmes(id)
);

-- INSERTS de Locações (Sem mudanças)
INSERT INTO locacoes (id_cliente, id_filme, data_locacao, data_devolucao) VALUES
(1, 5, '2025-09-20', '2025-09-25'),
(2, 3, '2025-09-21', NULL),
(3, 10, '2025-09-22', '2025-09-26'),
(4, 1, '2025-09-23', NULL),
(5, 7, '2025-09-24', '2025-09-28'),
(6, 2, '2025-09-25', NULL),
(7, 8, '2025-09-26', '2025-09-30'),
(8, 4, '2025-09-27', NULL),
(9, 6, '2025-09-28', '2025-10-02'),
(10, 9, '2025-09-29', NULL),
(11, 11, '2025-09-30', '2025-10-04'),
(12, 12, '2025-10-01', NULL),
(13, 13, '2025-10-02', '2025-10-06'),
(14, 14, '2025-10-03', NULL),
(15, 15, '2025-10-04', '2025-10-08'),
(16, 16, '2025-10-05', NULL),
(17, 17, '2025-10-06', '2025-10-10'),
(18, 18, '2025-10-07', NULL),
(19, 19, '2025-10-08', '2025-10-12'),
(20, 20, '2025-10-09', NULL);