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

-- Criação da tabela alunos
DROP TABLE IF EXISTS alunos;
CREATE TABLE alunos (

--  Detalhamento da definição da coluna 'id':
--  id              : Nome da coluna, identificador único para cada registro (seguindo convenção snake_case).
--  INT             : Tipo de dado, indica que a coluna armazenará números inteiros (valores de -2147483648 a 2147483647 no MariaDB).
--  AUTO_INCREMENT  : Atributo que faz o valor da coluna incrementar automaticamente a cada novo registro (começando de 1, a menos que configurado).
--  PRIMARY KEY     : Constraint que define a coluna como chave primária, garantindo unicidade e permitindo indexação rápida para buscas.
    id              INT AUTO_INCREMENT PRIMARY KEY ,

--  Detalhamento da definição da coluna 'nome':
--  nome            : Nome da coluna, representa o nome de uma entidade (ex.: nome de um aluno, seguindo convenção snake_case).
--  VARCHAR(100)    : Tipo de dado, texto de comprimento variável com limite de 100 caracteres (considerando o charset).
--  NOT NULL        : Constraint que impede que a coluna aceite valores nulos, tornando o campo obrigatório.
    nome            VARCHAR(100) NOT NULL          ,

--  Detalhamento da definição da coluna 'cpf':
--  cpf             : Nome da coluna, representa o CPF de uma entidade (ex.: de um aluno, seguindo convenção snake_case).
--  VARCHAR(14)    : Tipo de dado, texto de comprimento fixo com limite de 14 caracteres (formato 'XXX.XXX.XXX-XX', onde X é um dígito).
--  NOT NULL        : Constraint que impede que a coluna aceite valores nulos, tornando o campo obrigatório.
    cpf             VARCHAR(14)                    ,

--  Detalhamento da definição da coluna 'data_nascimento':
--  data_nascimento : Nome da coluna, representa a data de nascimento de uma entidade (ex.: de um aluno, seguindo convenção snake_case).
--  DATE            : Tipo de dado, armazena apenas datas no formato ano-mês-dia (ex.: '2025-06-06').
    data_nascimento DATE                           ,

--  Detalhamento da definição da coluna 'telefone':
--  telefone        : Nome da coluna, representa o telefone de contato de uma entidade (ex.: de um aluno, seguindo convenção snake_case).
--  VARCHAR(15)    : Tipo de dado, texto de comprimento variável com limite de 15 caracteres (formato '(XX) XXXXX-XXXX', onde X é um dígito).
--  NOT NULL        : Constraint que impede que a coluna aceite valores nulos, tornando o campo obrigatório.
    telefone        VARCHAR(15)                    ,

--  Detalhamento da definição da coluna 'email':
--  email           : Nome da coluna, representa o email de contato de uma entidade (ex.: de um aluno, seguindo convenção snake_case).
--  VARCHAR(100)    : Tipo de dado, texto de comprimento variável com limite de 100 caracteres.    
    email           VARCHAR(100)                   ,
    
--  Constraints UNIQUES garantem que não haja duplicatas                                                          ,
    CONSTRAINT check_cpf_formato_constraint      CHECK  (cpf REGEXP      '^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}$'         ),
    CONSTRAINT check_telefone_formato_constraint CHECK  (telefone REGEXP '^\\([0-9]{2}\\) [0-9]{5}-[0-9]{4}$'                )
--  Constraint para definir o formato do campo de email, garantindo que ele siga o padrão geral de emails (exemplo:
--  CONSTRAINT check_email_formato_constraint    CHECK  (email REGEXP    '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$' )
);

-- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Exibe a estrutura da tabela alunos e descreve os campos da tabela ------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
SHOW CREATE TABLE alunos;   
DESCRIBE alunos;    

-- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Inserção de dados de exemplo na tabela alunos -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO alunos (nome, cpf, data_nascimento, telefone, email) VALUES
( 'João',                  '123.456.789-00', '1990-01-01', '(11) 98765-4321', 'joao@email.com'              ),
( 'Maria de Lourdes',      '987.654.321-00', '1995-05-15', '(21) 91234-5678', 'm.lourdes@dominio.com'       ),
( 'Carlos Henrique Souza', '456.789.123-00', '1988-10-20', '(31) 99876-5432', 'carlos.h.souza@exemplo.com'  ),
( 'Ana',                   '321.654.987-00', '1992-03-30', '(41) 97654-3210', 'ana@abc.com'                 ),
( 'Fernanda',              '654.321.987-00', '1985-07-25', '(51) 96543-2109', 'fernanda.lima@empresa.com'   ),
( 'Luiz H.',               '159.753.486-21', '1998-12-12', '(61) 91234-0001', 'luizh@site.com'              ),
( 'Beatriz Almeida',       '258.369.147-85', '2000-04-18', '(71) 93456-7890', 'b.almeida@longdomain.com.br' ),
( 'Rafael dos Anjos',      '741.852.963-00', '1993-09-09', '(81) 90000-1234', 'rafael.anjos@email.com'      ),
( 'Patrícia S.',           '369.258.147-77', '1987-11-23', '(91) 98888-7777', 'patricia@xyz.com'            ),
( 'Eduardo Martins',       '852.741.963-55', '1991-06-05', '(85) 97777-8888', 'eduardo.martins@email.com'   );

-- Exibe os dados inseridos na tabela alunos
SELECT * FROM alunos;