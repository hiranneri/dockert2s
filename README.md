
# dockert2s

Esse projeto é um desafio de criar uma API que faz o gerenciamento de clientes, contêineres e movimentação. Foi desenvolvido em Java usando o Framework Spring Boot junto com JPA persistindo os dados no banco MySQL.
Neste projeto é utilizado o Spring Security que gerencia as autenticações e permissões nas rotas disponíveis. Com o usuário autenticado é gerado um token JWT que deve ser utilizado nos headers das requisições de forma Bearer Authentication.

## Rodando localmente

Clone o projeto

```bash
  git clone https://github.com/hiranneri/dockert2s.git
```

Entre no diretório resources do projeto

```bash
  cd dockert2s/src/main/java/resources
```

Crie os containers com o docker compose

```bash
  docker compose up -d
```

## Requisitos
- Java 11
- Docker e Docker Compose v2
- Maven

## Parametrização
Ao iniciar a aplicação pela primeira vez alguns dados como Tipo Movimentação, Categoria do Conteiner e dois usuários(admin e hiran) com as roles ADMIN e USER respectivamente já são criadas automaticamente no banco de dados.

| User      | Password  | Role
| --------- | -------   | ------
| hiran     | teste     | ROLE_USER
| admin     | teste     | ROLE_ADMIN


## Como testar?
Podemos usar a API realizando as requisições necessárias ou executando os testes de integração.
Para execução das urls segue o link do [Swagger](http://localhost:8080/swagger-ui/index.html) sendo disponível após a execução do comando **docker compose up** citado no tópico **Rodando Localmente**

Como executar os testes de integração:

Para os testes de integração, apenas execute o comando abaixo:

```bash
mvn test -P integration-tests
```
O comando acima irá buildar o projeto e executar os testes configurados para o profile integration-tests

## Decisões Técnicas
A escolha do framework para o desenvolvimento deste projeto foi fundamentada em dois principais critérios: primeiro, na base de conhecimento prévia que possuo, o que facilita a implementação; segundo, na capacidade do framework de suportar os conceitos de inversão de controle e injeção de dependências. Além disso, a escolha recai sobre o fato de que o framework é desenvolvido em uma linguagem amplamente adotada no mercado atual.

O projeto segue o padrão Model-Controller, onde a camada Model abstrai os conceitos de banco de dados, enquanto a camada Controller recebe as requisições e encaminha os dados para tratamento e validação na camada Service. Após a validação, os dados são persistidos ou consultados no banco de dados por meio da camada Repository.

Para garantir a segurança da API, o projeto faz uso do Spring Security, que gerencia a autenticação e autorização. Após a autenticação bem-sucedida de um usuário cadastrado, um Bearer Token (JWT) é gerado e incluído no corpo da resposta da URL de login, válido por 60 minutos.

Dada a natureza do negócio, que não requer operações de escrita/leitura massivas, e a necessidade de auditoria por meio de relatórios, optou-se por utilizar um banco de dados relacional, o MySQL, adequado aos objetivos da API.

A escolha do Docker foi motivada pela facilidade de empacotar e executar a aplicação e suas partes em ambientes isolados, conhecidos como containers. Esses containers são tratados pelo sistema operacional como processos individuais.
Além disso, a decisão de utilizar distroless (Sem Distro - Distribuição Linux) foi baseada em considerações de segurança e desempenho, uma vez que essas imagens são mais leves e não contêm componentes que possam introduzir vulnerabilidades de segurança.

Para os testes de integração que envolvem operações de escrita e leitura no banco de dados, foi escolhida a ferramenta TestContainer. Essa escolha se deu devido à sua capacidade de gerenciar o ciclo de vida do container durante a execução dos testes, garantindo a integridade e confiabilidade dos testes.

## Decisões do negócio
Durante o desenvolvimento foi entendido que, para evitarmos duplicação de “movimentações”, criamos a regra que não será possível ter mais de uma movimentação usando o mesmo tipo para o mesmo conteiner.
