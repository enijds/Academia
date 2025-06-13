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

-- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Seleciona a base de dados "academia" -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
USE academia;

-- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Criação da tabela treinos ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
DROP TABLE IF EXISTS treinos;
CREATE TABLE treinos (
--  id                 : Identificador único de cada treino
--  INT AUTO_INCREMENT : gera valores sequenciais automaticamente
--  PRIMARY KEY        : garante unicidade e indexação rápida
    id              INT AUTO_INCREMENT PRIMARY KEY,

--  aluno_id           : Referência ao aluno (chave estrangeira)
--  INT NOT NULL       : campo obrigatório
    aluno_id        INT NOT NULL                  ,

--  tipo_treino        : Ex.: 'Musculação', 'Aeróbico'
--  VARCHAR(50)        : texto de tamanho variável até 50 caracteres
--  NOT NULL           : campo obrigatório
    tipo_treino     VARCHAR(50) NOT NULL          ,

--  descricao          : Detalhes do treino
--  TEXT               : armazena textos longos
    descricao       TEXT                          ,

--  duracao_minutos    : Duração do treino em minutos
--  INT NOT NULL       : campo obrigatório
    duracao_minutos INT NOT NULL                  ,

--  data_inicio        : Data de início do treino (ano-mês-dia)
--  DATE NOT NULL      : campo obrigatório
    data_inicio     DATE NOT NULL                 ,

--  Garante que a duração seja sempre maior que zero
    CONSTRAINT check_duracao_positiva_constraint CHECK (duracao_minutos > 0) ,

--  Define chave estrangeira apontando para alunos(id) do campo id da tabela alunos
--  FOREIGN KEY        : estabelece a relação entre treinos e alunos
    CONSTRAINT fk_treinos_aluno FOREIGN KEY (aluno_id) REFERENCES alunos(id)
--  FOREIGN KEY (aluno_id) CONSTRAINT fk_treinos_aluno REFERENCES alunos(id)
);


-- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Exibe a estrutura da tabela treinos e descreve os campos da tabela treinos ---------------------------------------------------------------------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
SHOW CREATE TABLE treinos;
DESCRIBE treinos;

-- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Inserção de dados de exemplo na tabela treinos -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO treinos (aluno_id, tipo_treino, descricao, duracao_minutos, data_inicio) VALUES
( 1 , 'Musculação' , 'Treino de força focado em membros superiores'     , 60 , '2025-06-01' ) ,
( 2 , 'Aeróbico'   , 'Sessão de corrida contínua em esteira'            , 45 , '2025-06-02' ) ,
( 3 , 'HIIT'       , 'Séries de alta intensidade com intervalos curtos' , 30 , '2025-06-03' ) ,
( 4 , 'Musculação' , 'Treino de força focado em membros inferiores'     , 45 , '2025-06-04' ) ,
( 5 , 'Pilates'    , 'Exercícios de alongamento e fortalecimento'       , 50 , '2025-06-05' ) ;

-- Exibe os dados inseridos
SELECT * FROM treinos;