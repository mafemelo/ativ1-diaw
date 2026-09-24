GUIA DE ESTUDO — SPRING BOOT / JAVA

Objetivo
Aprender o processo de criação de um projeto Spring Boot do zero, entendendo o papel de cada parte e conseguindo adaptar o processo para atividades diferentes.

1. Criar o projeto no Spring Initializr

Acessar:
https://start.spring.io/

Configuração básica:
- Project: Maven
- Language: Java
- Spring Boot: usar a versão indicada pelo professor
- Packaging: Jar
- Java: usar a versão indicada pelo professor (na atividade estudada, Java 25)

Dependências que podem ser necessárias:
- Spring Web
- Thymeleaf
- OpenCSV, quando o projeto trabalhar com arquivos CSV

Depois:
1. Clicar em Generate.
2. Baixar o ZIP.
3. Extrair o projeto.
4. Abrir a pasta no VS Code.

2. Entender a estrutura do projeto

Estrutura principal:

src/
└── main/
    ├── java/
    │   └── pacote_do_projeto/
    │       ├── application/
    │       ├── controller/
    │       ├── model/
    │       └── service/
    └── resources/
        ├── data/
        ├── static/
        └── templates/

Arquivos importantes:
- application: classe principal que inicia o Spring Boot.
- controller: recebe as requisições do navegador e define as rotas.
- service: concentra a lógica do sistema.
- model: representa os dados/objetos usados pelo sistema.
- resources/data: arquivos de dados, como CSV.
- resources/static: arquivos estáticos, como CSS e imagens.
- resources/templates: páginas HTML usadas pelo Thymeleaf.
- pom.xml: configura o Maven e as dependências.
- application.properties: configurações da aplicação.

Outros itens:
- target: arquivos gerados durante a compilação.
- .idea: configurações da IDE.
- .mvn, mvnw e mvnw.cmd: arquivos do Maven Wrapper.
- test: testes automatizados.

Fluxo principal:
Navegador → Controller → Service → Model/dados → Controller → Thymeleaf → HTML → Navegador

Frase para memorizar:
Controller recebe, Service processa, Model representa, Thymeleaf mostra.

3. Executar o projeto

A classe principal possui @SpringBootApplication.

É possível executar pela IDE ou pelo Maven.

Depois, acessar:
http://localhost:8080/

Se ainda não existir uma rota configurada, pode aparecer uma página de erro/404. Isso não significa necessariamente que o projeto não iniciou.

4. Criar o Controller

Criar:
src/main/java/.../controller

Exemplo:

package com.seu.projeto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MeuController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}

O que cada parte faz:
- @Controller: informa ao Spring que a classe é um Controller.
- @GetMapping("/"): define que o método responde a GET /.
- return "index": indica que index.html deve ser carregado pelo Thymeleaf.

Para páginas HTML com Thymeleaf, usamos @Controller, e não @RestController.

5. Criar a primeira página HTML

Criar:
src/main/resources/templates/index.html

Exemplo:

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Meu Projeto</title>
</head>
<body>
    <h1>Olá, Spring Boot!</h1>
</body>
</html>

Fluxo:
GET /
→ Controller
→ return "index"
→ templates/index.html
→ página no navegador

Esta etapa já foi estudada na prática.

6. Criar o Model

O Model representa os dados do sistema.

Exemplo:

public class Produto {

    private String nome;
    private double preco;

    // getters e setters
}

A ideia:
- classe = tipo de objeto;
- atributo = informação do objeto;
- objeto = um registro específico.

Em um sistema de candidatos, por exemplo, poderia existir uma classe Candidato com informações como nome, partido e cargo.

7. Criar o Service

O Service concentra a lógica do sistema.

Exemplo conceitual:

@Service
public class ProdutoService {

    public List<Produto> listar() {
        // lógica para buscar/processar os produtos
    }
}

O Controller recebe a requisição e chama o Service.

Em um projeto com CSV, o Service pode:
- ler o arquivo;
- transformar linhas em objetos;
- filtrar dados;
- listar opções de filtros;
- retornar resultados.

8. Conectar Controller, Service e Model

Um Controller pode receber o Service por construtor:

@Controller
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }
}

Depois:

@GetMapping("/")
public String index(Model model) {
    List<Produto> produtos = service.listar();
    model.addAttribute("produtos", produtos);
    return "index";
}

Aqui:
- service.listar() busca/processa os dados;
- model.addAttribute() envia os dados para o HTML.

9. Thymeleaf

Thymeleaf permite que o HTML receba dados enviados pelo Controller.

No HTML:

<html xmlns:th="http://www.thymeleaf.org">

Para mostrar um valor:

<p th:text="${produto.nome}"></p>

Para repetir elementos:

<div th:each="produto : ${produtos}">
    <p th:text="${produto.nome}"></p>
</div>

Fluxo:
Controller envia dados
→ Thymeleaf recebe
→ HTML usa th:text, th:each, th:if etc.
→ navegador mostra a página

10. Formulários e filtros

Exemplo:

<form method="get" action="/">
    <input type="text" name="texto">
    <button type="submit">Pesquisar</button>
</form>

Controller:

@GetMapping("/")
public String index(@RequestParam(required = false) String texto) {
    // usar texto no filtro
    return "index";
}

Fluxo:
HTML envia parâmetro
→ Controller recebe
→ Service aplica a lógica
→ Controller envia resultado
→ Thymeleaf mostra

11. CSS e arquivos estáticos

Arquivos como CSS e imagens ficam em:

src/main/resources/static/

Exemplo:
static/css/style.css

No HTML:

<link rel="stylesheet" th:href="@{/css/style.css}">

Imagens também ficam dentro de static.

12. Trabalhar com CSV

Quando o projeto usa CSV:
- colocar o arquivo em resources/data;
- usar o Service para fazer a leitura;
- transformar os registros em objetos do Model;
- aplicar filtros no Service;
- enviar os resultados ao Controller;
- mostrar os resultados com Thymeleaf.

No projeto estudado, o README informa que o sistema lê dados de candidatos do TSE e que o Service carrega o CSV, resolve fotos, filtra candidatos e lista cargos e partidos.

13. Como pensar em uma atividade diferente

Não decorar nomes de classes ou códigos específicos.

Primeiro identificar:
1. Qual página o navegador precisa abrir?
2. Qual rota o Controller deve atender?
3. Quais dados existem? → Model
4. De onde os dados vêm?
5. Qual lógica precisa ser feita? → Service
6. Quais dados o HTML precisa receber?
7. Como o Thymeleaf vai mostrar esses dados?
8. Existem formulários/filtros?
9. Existem CSS, imagens ou outros arquivos?

Ordem geral:

Spring Initializr
→ estrutura
→ executar
→ Controller
→ HTML
→ Model
→ Service
→ conexão entre eles
→ Thymeleaf
→ filtros/formulários
→ CSS/imagens/dados

14. O que já foi feito no estudo

Já estudamos:
- criação do projeto pelo start.spring.io;
- configuração básica do Maven/Spring Boot/Java;
- estrutura de pastas;
- função de application, controller, service, model e resources;
- execução em localhost:8080;
- criação de um Controller;
- @Controller;
- @GetMapping;
- retorno de uma página Thymeleaf;
- criação de templates/index.html;
- fluxo navegador → Controller → HTML.

Próximas etapas:
- Model;
- Service;
- conexão Controller + Service + Model;
- Thymeleaf com dados reais;
- formulários e filtros;
- CSS e imagens;
- leitura de CSV;
- montagem completa de um projeto semelhante a uma atividade.

15. Regra principal para estudar

Não tentar decorar um projeto inteiro.

Entender o papel de cada camada e conseguir reconstruir o fluxo:

Navegador
↓
Controller
↓
Service
↓
Model / dados
↓
Controller
↓
Thymeleaf
↓
HTML
↓
Navegador
