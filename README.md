# PROJETO A3 CONTROLE DE CARGA

#### Alunos:

* Pietro Schuster Moreira
* Pedro Moreira Carvalho
* Ricardo Araujo Candido
* João Pedro Gonçalves
* Lucas Emanuel Fagundes

---

Projeto backend para simular o carregamento de embalagens em caminhões, respeitando o volume máximo suportado e mantendo a integridade dos fornecedores envolvidos. Desenvolvido com **Spring Boot**, **MySQL** e **Docker**.

## 📖 Sumário

* [🎯 Objetivos do Projeto](#🎯-objetivos-do-projeto)
* [🚀 Tecnologias Usadas](#🚀-tecnologias-usadas)
* [🔧 Como Rodar o Projeto](#🔧-como-rodar-o-projeto)
* [📌 Rotas Disponíveis](#📌-rotas-disponíveis)
* [🔮 Futuras Melhorias](#🔮-futuras-melhorias)

## 🎯 Objetivos do Projeto

1. **Gerenciar a alocação de embalagens em caminhões**, controlando o volume ocupado e impedindo excessos.
2. **Relacionar automaticamente fornecedores** com base nas embalagens presentes no caminhão.
3. **Registrar a quantidade de cada embalagem** carregada via uma entidade intermediária (`CaminhaoEmbalagem`).
4. **Permitir remoção parcial ou total de embalagens**, ajustando dinamicamente o volume e os fornecedores.
5. **Evitar inconsistências**, garantindo que as operações respeitem a lógica de negócio (ex: não ultrapassar volume máximo).

## 🚀 Tecnologias Usadas

* **Spring Boot 3.3.x**
* **MySQL 9.2.x**
* **Docker**
* **JPA/Hibernate**

## 🔧 Como Rodar o Projeto

### 1️⃣ Clone o Repositório

```bash
git clone https://github.com/Schusteerr/CargoManagerAPI.git
cd CargoManagerAPI
```

### 2️⃣ Compile o Projeto

```bash
mvn clean install
```

### 3️⃣ Configure o Banco com Docker

```bash
docker-compose up -d
```

### 4️⃣ Rode a Aplicação

```bash
mvn spring-boot:run
```

## 📌 Rotas Disponíveis

### 📦 Embalagens

* **POST /embalagens** → Cadastrar nova embalagem
* **GET /embalagens** → Listar todas as embalagens
* **GET /embalagens/{codigo}** → Obter detalhes de uma embalagem pelo código
* **PUT /embalagens/{codigo}** → Atualizar dados de uma embalagem existente
* **DELETE /embalagens/{codigo}** → Remover uma embalagem do sistema

### 🧾 Fornecedores

* **POST /fornecedores** → Cadastrar novo fornecedor
* **GET /fornecedores** → Listar todos os fornecedores
* **GET /fornecedores/{codigo}** → Detalhes de um fornecedor específico
* **PUT /fornecedores/{codigo}** → Atualizar informações de um fornecedor
* **DELETE /fornecedores/{codigo}** → Remover um fornecedor pelo código

### 🚚 Caminhões

* **POST /caminhoes** → Cadastrar um novo caminhão
* **GET /caminhoes** → Listar todos os caminhões cadastrados
* **GET /caminhoes/{placa}** → Detalhes completos de um caminhão
* **PUT /caminhoes/{placa}/embalagens/adicionar** → Adicionar embalagens ao caminhão especificando quantidade
* **PUT /caminhoes/{placa}/embalagens/remover** → Remover determinada quantidade de embalagens de um caminhão


## 🔮 Futuras Melhorias

* Implementação de autenticação (JWT)
* Validação e consistência
* Frontend para visualização em tempo real
* Exportação dos arquivos de carga

---