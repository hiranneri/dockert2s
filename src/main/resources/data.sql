INSERT INTO tiposmovimentacao (nome, status)
SELECT nome, status
FROM (VALUES
  ('DESCARGA',true),
  ('EMBARQUE',true),
  ('GATE_IN', true),
  ( 'GATE_OUT', true),
  ( 'PESAGEM', true),
  ( 'PILHA', true),
  ( 'POSICIONAMENTO', true),
  ( 'SCANNER', true)
) AS tipos (nome,status)
WHERE NOT EXISTS (
  SELECT 1
  FROM tiposmovimentacao
);

INSERT INTO usuarios (authorities,name,password,status,username)
SELECT authorities,name,password,status,username
FROM (VALUES
  ('ROLE_ADMIN', 'HIRAN', '$2a$10$tlFWUcQeXmqMSnvLu1j6meKNZG3yyiBTUtv21HrNJSPDZx7XLTIXG', true,'hiran'),
  ('ROLE_USER', 'ADMIN', '$2a$10$tlFWUcQeXmqMSnvLu1j6meKNZG3yyiBTUtv21HrNJSPDZx7XLTIXG',true, 'admin')
) AS usuarios (authorities,name,password,status,username)
WHERE NOT EXISTS (
  SELECT 1
  FROM usuarios
);