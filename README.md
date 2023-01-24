[![Build Status](https://app.travis-ci.com/muriloalvesdev/transaction.svg?branch=main)](https://app.travis-ci.com/muriloalvesdev/transaction)
[![codecov](https://codecov.io/gh/muriloalvesdev/transaction/branch/main/graph/badge.svg?token=HNYBKIWJ30)](https://codecov.io/gh/muriloalvesdev/transaction)

Para compilar e executar o projeto você precisa ter instalado
-
 - [Maven](https://maven.apache.org/)
 - [Docker](https://docs.docker.com/get-docker/)

Instalacao
-
- Clone o repositório: `$ git clone https://github.com/muriloalvesdev/transaction.git`
- Acesse o diretório do projeto: `$ cd transactions`

Instando dependências e executando os testes com Maven:
-
- Para instalar as dependências e executar os testes, utilize o comando: `$ mvn clean package`

Executando a aplicacao com o Java manualmente
-
- Para executar você pode utilizar o java através do terminal (precisa ter uma versão compatível com a versão 17 do Java). Execute: `$ java -jar target/transaction-0.0.1-SNAPSHOT.jar `

Executando a aplicacao com o Docker
-
1. Execute o comando `$ mvn clean package` caso não tenha executado.
2. Dê permissao de execucao para o arquivo `docker-compose.yml` através do comando `$ chmod u+x docker-compose.yml`.
3. Agora execute o comando `$ docker compose up -d`. Aguarde até que faca download das imagens do projeto e do banco de dados h2.
4. Como a aplicacão está rodando localmente, os endpoints das APIs estão com a base_url como `localhost:8080`.

Executando a aplicacao sem a necessidade de instalacao
-
Como facilitador deixei uma versao deployada no [Scalingo](https://scalingo.com/), como é uma infraestrutura gratuita, talvez na primeira requisicão tenha um delay.
Veja algumas informacoes sobre o Scalingo na [documentacao oficial](https://doc.scalingo.com/) caso tenha interesse.

- Utilize a documentacao da API disponibilizada [aqui](https://app.swaggerhub.com/apis/muriloalvesdev/transaction-documentation/1.0.0) para facilitar a utilizacão dos endpoints.

Sugestão de ferramentas para consumo das APIs
-
- [Postman](https://www.postman.com/)
- [Insominia](https://insomnia.rest/)
