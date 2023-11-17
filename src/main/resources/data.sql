INSERT IGNORE INTO tiposmovimentacao  (nome, status)
SELECT 'DESCARGA', 1
UNION
SELECT 'EMBARQUE', 1
UNION
SELECT 'GATE_IN', 1
UNION
SELECT 'GATE_OUT', 1
UNION
SELECT 'PESAGEM', 1
UNION
SELECT 'PILHA', 1
UNION
SELECT 'POSICIONAMENTO', 1
UNION
SELECT 'SCANNER', 1
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM tiposmovimentacao);

INSERT INTO usuarios
SELECT *
FROM (
  VALUES
  ROW(NULL,'ROLE_ADMIN', 'HIRAN', '$2a$10$tlFWUcQeXmqMSnvLu1j6meKNZG3yyiBTUtv21HrNJSPDZx7XLTIXG', 1,'hiran'),
  ROW(NULL , 'ROLE_USER', 'ADMIN', '$2a$10$tlFWUcQeXmqMSnvLu1j6meKNZG3yyiBTUtv21HrNJSPDZx7XLTIXG',1, 'admin')
) source_data
WHERE NOT EXISTS (
  SELECT NULL
  FROM usuarios
);