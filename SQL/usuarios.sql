-- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Convenções de nomenclatura para o SQL:
-- Colunas (snake_case)             : saldo_atual; data_criacao; nome_completo; preco_unitario; status_pedido;                                                                                       
-- Constantes (UPPER_SNAKE_CASE)    : MAX_RETRY_COUNT=5; API_KEY='xyz123'; GRAVIDADE_TERRA=9.81; TAXA_JUROS_ANUAL=0.05; LIMITE_USUARIOS=1000;                                                        
-- Constraints (sufixo _constraint) : check_dados_validos_constraint; check_rede_disponivel_constraint; check_saldo_positivo_constraint; check_email_unico_constraint; check_data_futura_constraint; 
-- Funções (snake_case, verbos)     : calcular_total(); buscar_cliente(); gerar_relatorio_anual(); atualizar_estoque(); validar_usuario();                                                           
-- Parâmetros (snake_case)          : id_transacao; valor_total; data_inicio; nome_usuario; codigo_produto;                                                                                          
-- Schemas (lowercase)              : empresa_produto; sistema_banco; gestao_vendas; controle_estoque; modulo_financeiro;                                                                            
-- Tabelas (snake_case, plural)     : pedidos_vendas.sql; clientes.sql; produtos_estoque.sql; usuarios_sistema.sql; vendas_anuais.sql;                                                               
-- Tabelas teste (sufixo _test)     : validador_cpf_test; processador_pagamento_test; calculadora_imposto_test; gerenciador_estoque_test; autenticador_usuario_test;                                 
-- Variáveis (snake_case)           : id_transacao; saldo_atual; total_calculado; ultima_data; codigo_gerado;                                                                                        
-- Views (prefixo vw_)              : vw_pedidos_vendas; vw_clientes_ativos; vw_produtos_disponiveis; vw_vendas_mensais; vw_usuarios_bloqueados;                                                     
-- 
-- Arquivos de criação de tabelas (snake_case, plural, .sql): pedidos_vendas.sql; clientes.sql; produtos_estoque.sql; usuarios_sistema.sql; vendas_anuais.sql;
-- 
-- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Tabela Comparativa de Charsets no MariaDB/MySQL:
-- +-----------------------+-------------------------------+------------------+--------------------------------+-----------------------------+
-- | Charset               | Descrição                     | Suporte Unicode  | Caso de Uso                    | Limitações                  |
-- +-----------------------+-------------------------------+------------------+--------------------------------+-----------------------------+
-- | utf8mb4               | Charset Unicode completo      | Sim (todos)      | Aplicações globais, emojis     | Usa mais espaço             |
-- | utf8mb4_uca1400_ai_ci | Collation avançada p/ utf8mb4 | Sim (todos)      | Ordenação linguística precisa  | Desempenho mais lento       |
-- | utf8 (utf8mb3)        | Charset Unicode limitado      | Sim (BMP)        | Dados legados, apps simples    | Não suporta emojis          |
-- | ascii                 | Charset básico (7 bits)       | Não              | Dados simples, apenas ASCII    | Muito limitado (127 chars)  |
-- | utf16                 | Charset Unicode (16 bits)     | Sim (todos)      | Dados Unicode, apps específicas| Usa mais espaço que utf8mb4 |
-- | cp65001               | Alias p/ UTF-8 (Windows)      | Sim (todos)      | Integração com sistemas Windows| Não nativo no MariaDB       |
-- | cp1252                | Charset Windows p/ pt_BR      | Não              | Texto em português brasileiro  | Sem suporte a Unicode       |
-- +-----------------------+-------------------------------+------------------+--------------------------------+-----------------------------+

-- Tabela com os principais de Tipos de Dados do MariaDB:
-- +--------------+----------------------------+-----------------------------+-------------------------------------+
-- | Tipo de Dado | Descrição                  | Exemplo de Uso              | Limitações                          |
-- +--------------+----------------------------+-----------------------------+-------------------------------------+
-- | INT          | Inteiro (4 bytes)          | id INT PRIMARY KEY          | -2147483648 a 2147483647            |
-- | BIGINT       | Inteiro grande (8 bytes)   | saldo BIGINT                | -2^63 a 2^63-1                      |
-- | DECIMAL      | Número decimal preciso     | preco DECIMAL(10,2)         | Precisão definida (ex.: 10 dígitos) |
-- | VARCHAR      | Texto de comprimento var.  | nome VARCHAR(100)           | Máx. 65.535 bytes (com charset)     |
-- | TEXT         | Texto longo                | descricao TEXT              | Máx. 65.535 bytes, sem índice full  |
-- | DATE         | Data (ano-mês-dia)         | data_nascimento DATE        | 1000-01-01 a 9999-12-31             |
-- | DATETIME     | Data e hora                | criado_em DATETIME          | 1000-01-01 00:00:00 a 9999-12-31    |
-- | TIMESTAMP    | Data/hora com fuso horário | atualizado_em TIMESTAMP     | 1970-01-01 a 2038-01-19 (32 bits)   |
-- | BOOLEAN      | Verdadeiro/Falso           | ativo BOOLEAN               | Armazenado como TINYINT (0 ou 1)    |
-- | JSON         | Dados no formato JSON      | config JSON                 | Validação limitada no MariaDB       |
-- +--------------+----------------------------+-----------------------------+-------------------------------------+
-- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- Seleciona a base de dados "academia"
USE academia;

-- Criação da tabela usuarios
DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (

    -- detalhamento da coluna id_usuario
    -- Tipo INT, auto incremento, chave primária
    -- AUTO_INCREMENT permite que o valor seja gerado automaticamente
    -- PRIMARY KEY define que esta coluna é a chave primária da tabela
    -- A chave primária é um identificador único para cada registro na tabela
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,

    -- detalhamento da coluna nome_usuario
    -- Tipo VARCHAR com tamanho máximo de 50 caracteres
    -- NOT NULL indica que este campo é obrigatório e não pode ser nulo
    -- VARCHAR é usado para armazenar strings de comprimento variável
    -- O tamanho máximo de 50 caracteres é suficiente para nomes de usuário comuns
    nome_usuario VARCHAR(50) NOT NULL,

    -- detalhamento da coluna senha_usuario
    -- Tipo VARCHAR com tamanho máximo de 255 caracteres
    -- NOT NULL indica que este campo é obrigatório e não pode ser nulo
    -- VARCHAR é usado para armazenar strings de comprimento variável
    senha_usuario VARCHAR(255) NOT NULL,

    -- detalhamento da coluna dat_criacao
    -- Tipo DATETIME, com valor padrão de CURRENT_TIMESTAMP
    -- DATETIME armazena data e hora no formato 'YYYY-MM-DD HH:MM:SS'
    -- DEFAULT CURRENT_TIMESTAMP define que o valor padrão será a data e hora atual no momento da inserção
    -- Isso é útil para registrar quando o usuário foi criado
    data_criacao_usuario DATETIME DEFAULT CURRENT_TIMESTAMP,

    -- detalhamento da coluna ativo
    -- Tipo BOOLEAN, com valor padrão TRUE
    -- BOOLEAN é usado para armazenar valores lógicos (verdadeiro ou falso)
    -- DEFAULT TRUE define que o valor padrão será verdadeiro, indicando que o usuário está ativo por padrão
    -- Isso é útil para controlar o status do usuário sem precisar de uma coluna separada para status
    ativo_usuario BOOLEAN DEFAULT TRUE

-- Engine InnoDB é o mecanismo de armazenamento padrão do MySQL que
-- oferece suporte a transações, chaves estrangeiras e integridade referencial
-- COLLATE=utf8mb4_uca1400_ai_ci define o conjunto de caracteres e a coluna de collation para usar
-- CHARSET=utf8mb4 é um conjunto de caracteres que suporta todos os caracteres Unicode, incluindo emojis
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Exibe a estrutura da tabela usuarios
SHOW CREATE TABLE usuarios;   
DESCRIBE usuarios;
 
-- Insere mais usuários de teste com senha armazenada como hash SHA2-256
INSERT INTO usuarios (nome_usuario, senha_usuario, data_criacao_usuario, ativo_usuario) 
VALUES
('usuario', SHA2('senha', 256), NOW(), TRUE),
('x7q9v2k1', SHA2('p@ssw0rdA1', 256), NOW(), TRUE),
('m4t8z6w3', SHA2('S3nh@B2x', 256), NOW(), TRUE),
('b2r5n8j7', SHA2('Us3rC!23', 256), NOW(), TRUE),
('f9k1s3d2', SHA2('T3stD#45', 256), NOW(), TRUE);

-- Exibe os dados inseridos na tabela usuarios
SELECT * FROM usuarios;