# Medical Clinic Control API

Voll Med é uma empresa fictícia na área da saúde. Esta é uma API REST que controla o fluxo de serviços de uma clínica médica. O aplicativo possui um banco de dados para armazenar informações sobre médicos, pacientes (com endereços e contatos), agenda e horários de funcionamento.

## Referências

- [Infoq - Design de Domínio Orientado a Eventos](https://www.infoq.com/minibooks/domain-driven-design-quickly/)
- [Baeldung - Tutorial REST com Spring](https://www.baeldung.com/rest-with-spring-series)
- [Alura - Curso de Spring Boot](https://www.alura.com.br/conteudo/spring-boot-3-desenvolva-api-rest-java--amp?gclid=CjwKCAjw4ZWkBhA4EiwAVJXwqQHOzj952acV9dMcEmBZ2YMzjIxu33FRweAcBhk6qf_-BnZWegOPsBoC_J8QAvD_BwE)
- [Swagger - Documentação de API](https://swagger.io/)
- [Insomnia - Documentação](https://docs.insomnia.rest/)
- [HTTP Status Dogs - Ilustrações de Códigos de Status HTTP](https://http.dog/)

## Autores

- [@lasbrDev](https://github.com/lasbrDev)

## Tecnologias Utilizadas

**Back-end:** Java, Spring Boot

**Banco de Dados:** MySQL

**Conteinerização** Docker

**Cliente de Teste de API:** Insomnia

## Docker Compose

```yaml
version: '3.8'

services:
  vollmed-mysql:
    image: mysql:latest
    container_name: vollmed-mysql
    environment:
      MYSQL_ROOT_USERNAME: root
      MYSQL_ROOT_PASSWORD: lasBr01
      MYSQL_DATABASE: vollmed_api
    networks:
      - REDEVOL
    ports:
      - "3306:3306"
    volumes:
      - vollmed-mysql-data:/var/lib/mysql

  flyway:
    image: flyway/flyway:latest
    container_name: vollmed-flyway
    environment:
      - FLYWAY_URL=jdbc:mysql://vollmed-mysql:3306/vollmed_api
      - FLYWAY_USER=root
      - FLYWAY_PASSWORD=lasBr01
    networks:
      - REDEVOL

networks:
  REDEVOL:
    driver: bridge

volumes:
  vollmed-mysql-data:
  ```

Neste arquivo docker-compose.yml, está configurando dois serviços: vollmed-mysql e flyway. O primeiro é para o banco de dados MySQL, enquanto o segundo é para as migrações usando o Flyway.

No MySQL, você especifica as credenciais do root, o nome do banco de dados, expõe a porta 3306 e usa um volume para persistir os dados. O serviço Flyway é configurado para se conectar ao MySQL e aplicar as migrações.

## Aprendizados

Neste projeto, pude consolidar ainda mais os conceitos de uma API REST, importantes princípios de arquitetura, principalmente o Design Driven Domain, que é crucial em projetos profissionais. Implementei validações nas regras de negócios e desenvolvi uma documentação elegante. Além disso, utilizei o Flyway para controle de migrações e subi duas instâncias no Docker, uma para o MySQL e outra para o Flyway. O token JWT foi adotado para autenticação, pois a API é Stateless. Trata-se de uma API para controle de clínica médica, abrangendo cadastro de pacientes, médicos com suas especialidades, agendamento de consultas com validações, tratamento de exceções e testes automatizados.


## Licença

[MIT](LICENSE)