/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  enio
 * Created: 13 de jun. de 2025
 */

-- Limita recursos do usuário
ALTER USER 'aplicacao'@'localhost' WITH
    MAX_CONNECTIONS_PER_HOUR 1000  -- 100 connections/hour (~1-2 per minute)
    MAX_QUERIES_PER_HOUR 5000      -- 500 queries/hour (~8 per minute)
    MAX_USER_CONNECTIONS 5        -- 5 simultaneous connections
    MAX_UPDATES_PER_HOUR 2000;     