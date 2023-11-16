INSERT IGNORE INTO tiposmovimentacao  (nome)
SELECT 'DESCARGA'
UNION
SELECT 'EMBARQUE'
UNION
SELECT 'GATE_IN'
UNION
SELECT 'GATE_OUT'
UNION
SELECT 'PESAGEM'
UNION
SELECT 'PILHA'
UNION
SELECT 'POSICIONAMENTO'
UNION
SELECT 'SCANNER'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM tiposmovimentacao);

INSERT INTO categorias
SELECT * FROM ( SELECT NULL, 'IMPORTACAO' UNION ALL SELECT NULL, 'EXPORTACAO') data
WHERE NOT EXISTS ( SELECT NULL FROM categorias);

INSERT INTO usuarios
SELECT *
FROM (
  VALUES
  ROW(NULL,'ROLE_ADMIN', 'HIRAN', '$2a$10$tlFWUcQeXmqMSnvLu1j6meKNZG3yyiBTUtv21HrNJSPDZx7XLTIXG', 'hiran'),
  ROW(NULL , 'ROLE_USER', 'ADMIN', '$2a$10$tlFWUcQeXmqMSnvLu1j6meKNZG3yyiBTUtv21HrNJSPDZx7XLTIXG', 'admin')
) source_data
WHERE NOT EXISTS (
  SELECT NULL
  FROM usuarios
);