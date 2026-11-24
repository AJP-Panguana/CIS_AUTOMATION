# CIS Automation

![Selenium](https://img.shields.io/badge/Selenium-4.43-43B02A?logo=selenium&logoColor=white)
![Java](https://img.shields.io/badge/Java-Maven-ED8B00?logo=openjdk&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-7.12-FF7F00)
![Python](https://img.shields.io/badge/Python-em%20migração%20%2F%20migrating-3776AB?logo=python&logoColor=white)
![Status](https://img.shields.io/badge/status-em%20evolução%20%2F%20evolving-blue)

**[Português](#-português) · [English](#-english)**

---

## 🇵🇹 Português

### Bem-vindo

Olá QA, QC, developers, curiosos e entusiastas de automação: **sejam bem-vindos**.

O **CIS Automation** é um projeto de testes automatizados *end-to-end* para a interface web (front-end) do sistema CIS. O objetivo é simples: apanhar os bugs antes dos utilizadores.

### A evolução do projeto

| Fase | Descrição |
|------|-----------|
| **1. Java** | O código de automação foi **escrito inicialmente em Java**, com Selenium WebDriver, TestNG e o padrão Page Object Model. |
| **2. Python** | O projeto está **em migração para Python**, para ter testes mais simples de escrever, ler e manter. |
| **3. Framework** | Esta estrutura está a **tornar-se uma framework de testes Selenium para front-end**, reutilizável, organizada por módulos e pronta a crescer. |

### Para quem é

- **QA (Quality Assurance):** garantir que o processo de desenvolvimento produz software com qualidade, através de testes de regressão automatizados e repetíveis.
- **QC (Quality Control):** verificar o produto final, ecrã a ecrã e fluxo a fluxo, antes de cada entrega.

### Tecnologias

| Ferramenta | Função |
|------------|--------|
| Selenium WebDriver 4.43 | Automação do navegador |
| Java + Maven | Linguagem inicial e gestão de dependências |
| TestNG 7.12 | Execução e organização dos testes |
| Page Object Model (POM) | Separação entre páginas e testes |
| Log4j2 / ExtentReports | Logs e relatório HTML |
| JSON / Excel | Dados de teste externos |
| Python | Linguagem alvo da migração |

### Módulos cobertos

| Módulo | Página (Page Object) | Testes |
|--------|----------------------|--------|
| Login | `LoginPage` | `LoginTest` |
| Produtos | `ProductPage` | `CreateProductTest`, `UpdateProductTest`, `DeleteProductTest` |
| Sinistros (Claims) | `ClaimsPage` | `ClaimsTest` |
| Subscrições | `SubscriptionPage` | `SubscriptionTest` |
| Prestadores (Providers) | `ProviderPage` | `ProviderTest` |
| Finanças | `FinancePage` | `FinanceTest` |
| Disco (Disk) | `DiskPage` | `DiskTest` |

### Estrutura do projeto

```
src
├── main/java/mz/co/maximo/cis/          # ⚙️ FRAMEWORK (o motor, reutilizável)
│   ├── core/config/                     # ConfigManager: ambientes e credenciais
│   ├── core/driver/                     # DriverManager (ThreadLocal), DriverFactory, BrowserType
│   ├── core/report/                     # ExtentManager: relatório HTML
│   ├── core/session/                    # Actor, SessionManager, ScenarioContext: vários utilizadores
│   ├── components/                      # Table, Modal, Toast: peças de UI reutilizáveis
│   ├── pages/                           # Page Objects (base, auth, common, por módulo)
│   ├── models/                          # Modelos de dados (ex.: User)
│   └── utils/                           # Waits, screenshots, JSON, Excel
└── test
    ├── java/mz/co/maximo/cis/           # 🧪 TESTES (usam o motor)
    │   ├── base/                        # BaseTest: abre e fecha o navegador
    │   ├── tests/                       # Testes por módulo
    │   ├── dataproviders/               # Dados para testes parametrizados
    │   └── listeners/                   # Listeners do TestNG
    └── resources/
        ├── config/                      # config.properties + environments/ (qa, uat, prod)
        ├── testdata/                    # users, products, claims (JSON)
        └── suites/                      # smoke.xml, regression.xml, e2e.xml
```

### Como executar (versão Java)

**Pré-requisitos:** JDK, Maven e Google Chrome instalados.

```bash
git clone https://github.com/AJP-Panguana/CIS_AUTOMATION.git
cd CIS_AUTOMATION
```

**Credenciais:** copie `src/test/resources/config/local.properties.example` para `local.properties` (que o git ignora) e preencha `user.email` e `user.password`. Em alternativa, use as variáveis de ambiente `CIS_USER_EMAIL` e `CIS_USER_PASSWORD`.

```bash
mvn clean test                          # regressão completa em QA
mvn clean test -Dsuite=smoke            # só os testes smoke
mvn clean test -Dsuite=smoke -Denv=uat  # smoke em UAT
mvn clean test -Dbrowser=firefox -Dheadless=true
```

**Resultados:** relatório HTML em `target/reports/cis-report.html`, screenshots das falhas em `target/screenshots/` e logs em `target/logs/`.

Também pode correr diretamente no IntelliJ IDEA: clique com o botão direito numa classe de teste e escolha **Run**.

### Cenários com vários utilizadores (workflows)

Processos reais passam por várias pessoas: o comercial cria o cliente, o supervisor aprova, o financeiro analisa e o comercial continua. Cada **ator** tem o seu próprio navegador, que fica aberto e com login feito durante todo o cenário. Os dados que passam de um ator para o outro (ex.: `clienteId`) ficam no `ScenarioContext`, e cada ator abre o registo diretamente por esse ID. No fim do cenário todos os navegadores fecham, mesmo que o teste falhe, e em caso de falha há um screenshot de cada ator no relatório.

```java
Actor comercial  = sessions.open("comercial",  BrowserType.CHROME,  "comercial");
Actor financeiro = sessions.open("financeiro", BrowserType.FIREFOX, "financeiro");

context.put("clienteId", comercial.step("Cria cliente").on(ClientesPage.class).criar(dados));
financeiro.step("Aprova").on(FinanceiroPage.class).aprovar(context.get("clienteId"));
comercial.step("Continua").on(ClientesPage.class).abrir(context.get("clienteId"));
```

As credenciais de cada perfil ficam em `local.properties` (`users.<perfil>.email` / `users.<perfil>.password`).

**Selenium Grid:** `docker compose up -d` e depois `mvn clean test -Dsuite=e2e -Dgrid.url=http://localhost:4444`.

> O Opera Mini não é suportado pelo Selenium (é um navegador móvel com renderização no servidor). Para testes em telemóvel, usar Appium.

### Roadmap

- [x] Estrutura base com Page Object Model
- [x] Page Objects e testes por módulo
- [x] Namespace único `mz.co.maximo.cis`, com o motor separado dos testes
- [x] Suites smoke e regressão
- [x] `DriverManager` thread-safe: execução em paralelo, Chrome / Firefox / Edge, modo headless
- [x] Ambientes configuráveis (QA / UAT / PROD)
- [x] `BasePage` completa e componentes reutilizáveis (Table, Modal, Toast)
- [x] Logging (Log4j2) e relatório HTML (ExtentReports) com screenshot em cada falha
- [ ] Migração completa para Python (pytest + Selenium)
- [x] Cenários multi-utilizador (Actor, SessionManager, ScenarioContext) e Selenium Grid
- [x] CI com GitHub Actions (compilação em cada push; testes E2E a pedido)

### Contacto

Tem perguntas ou ideias, ou quer simplesmente falar de automação? Fale comigo. Gosto de partilhar conhecimento e de aprender com os outros.

> Ainda não sou casado: por agora ando a namorar bugs e a resolvê-los um a um. 🐞

---

## 🇬🇧 English

### Welcome

Hi QA, QC, developers, curious minds and automation enthusiasts: **you're welcome here**.

**CIS Automation** is an *end-to-end* automated test project for the CIS web interface (front-end). The goal is simple: catch the bugs before the users do.

### Project evolution

| Phase | Description |
|-------|-------------|
| **1. Java** | The automation code was **initially written in Java**, using Selenium WebDriver, TestNG and the Page Object Model pattern. |
| **2. Python** | The project is **being migrated to Python**, so tests are simpler to write, read and maintain. |
| **3. Framework** | This structure is **becoming a Selenium front-end testing framework**: reusable, organized by module and ready to grow. |

### Who it's for

- **QA (Quality Assurance):** making sure the development process produces quality software, through repeatable automated regression tests.
- **QC (Quality Control):** checking the final product, screen by screen and flow by flow, before each release.

### Tech stack

| Tool | Purpose |
|------|---------|
| Selenium WebDriver 4.43 | Browser automation |
| Java + Maven | Initial language and dependency management |
| TestNG 7.12 | Test execution and organization |
| Page Object Model (POM) | Keeps pages and tests separate |
| Log4j2 / ExtentReports | Logs and HTML report |
| JSON / Excel | External test data |
| Python | Migration target language |

### Covered modules

| Module | Page Object | Tests |
|--------|-------------|-------|
| Login | `LoginPage` | `LoginTest` |
| Products | `ProductPage` | `CreateProductTest`, `UpdateProductTest`, `DeleteProductTest` |
| Claims | `ClaimsPage` | `ClaimsTest` |
| Subscriptions | `SubscriptionPage` | `SubscriptionTest` |
| Providers | `ProviderPage` | `ProviderTest` |
| Finance | `FinancePage` | `FinanceTest` |
| Disk | `DiskPage` | `DiskTest` |

### Project structure

```
src
├── main/java/mz/co/maximo/cis/          # ⚙️ FRAMEWORK (the engine, reusable)
│   ├── core/config/                     # ConfigManager: environments and credentials
│   ├── core/driver/                     # DriverManager (ThreadLocal), DriverFactory, BrowserType
│   ├── core/report/                     # ExtentManager: HTML report
│   ├── core/session/                    # Actor, SessionManager, ScenarioContext: multi-user
│   ├── components/                      # Table, Modal, Toast: reusable UI pieces
│   ├── pages/                           # Page Objects (base, auth, common, per module)
│   ├── models/                          # Data models (e.g. User)
│   └── utils/                           # Waits, screenshots, JSON, Excel
└── test
    ├── java/mz/co/maximo/cis/           # 🧪 TESTS (use the engine)
    │   ├── base/                        # BaseTest: opens and closes the browser
    │   ├── tests/                       # Tests per module
    │   ├── dataproviders/               # Data for parameterized tests
    │   └── listeners/                   # Listeners do TestNG
    └── resources/
        ├── config/                      # config.properties + environments/ (qa, uat, prod)
        ├── testdata/                    # users, products, claims (JSON)
        └── suites/                      # smoke.xml, regression.xml, e2e.xml
```

### How to run (Java version)

**Prerequisites:** JDK, Maven and Google Chrome installed.

```bash
git clone https://github.com/AJP-Panguana/CIS_AUTOMATION.git
cd CIS_AUTOMATION
```

**Credentials:** copy `src/test/resources/config/local.properties.example` to `local.properties` (git-ignored) and fill in `user.email` and `user.password`. Alternatively, use the `CIS_USER_EMAIL` and `CIS_USER_PASSWORD` environment variables.

```bash
mvn clean test                          # full regression on QA
mvn clean test -Dsuite=smoke            # smoke tests only
mvn clean test -Dsuite=smoke -Denv=uat  # smoke on UAT
mvn clean test -Dbrowser=firefox -Dheadless=true
```

**Results:** HTML report at `target/reports/cis-report.html`, failure screenshots in `target/screenshots/` and logs in `target/logs/`.

You can also run it straight from IntelliJ IDEA: right-click a test class and choose **Run**.

### Multi-user scenarios (workflows)

Real processes go through several people: sales creates the client, a supervisor approves, finance reviews, and sales carries on. Each **actor** has their own browser, kept open and logged in for the whole scenario. Data handed between actors (e.g. `clienteId`) lives in `ScenarioContext`, and each actor opens the record directly by that id. All browsers close at the end of the scenario, even when it fails, and on failure the report has one screenshot per actor.

```java
Actor comercial  = sessions.open("comercial",  BrowserType.CHROME,  "comercial");
Actor financeiro = sessions.open("financeiro", BrowserType.FIREFOX, "financeiro");

context.put("clienteId", comercial.step("Create client").on(ClientesPage.class).criar(dados));
financeiro.step("Approve").on(FinanceiroPage.class).aprovar(context.get("clienteId"));
comercial.step("Continue").on(ClientesPage.class).abrir(context.get("clienteId"));
```

Credentials per role go in `local.properties` (`users.<role>.email` / `users.<role>.password`).

**Selenium Grid:** `docker compose up -d`, then `mvn clean test -Dsuite=e2e -Dgrid.url=http://localhost:4444`.

> Opera Mini is not supported by Selenium (it is a mobile browser that renders on Opera's servers). For mobile testing, use Appium.

### Roadmap

- [x] Base structure with Page Object Model
- [x] Page Objects and tests per module
- [x] Single `mz.co.maximo.cis` namespace, engine separated from tests
- [x] Smoke and regression suites
- [x] Thread-safe `DriverManager`: parallel runs, Chrome / Firefox / Edge, headless mode
- [x] Configurable environments (QA / UAT / PROD)
- [x] Full `BasePage` and reusable components (Table, Modal, Toast)
- [x] Logging (Log4j2) and HTML report (ExtentReports) with a screenshot on every failure
- [ ] Full migration to Python (pytest + Selenium)
- [x] Multi-user scenarios (Actor, SessionManager, ScenarioContext) and Selenium Grid
- [x] CI with GitHub Actions (compile on every push; E2E tests on demand)

### Contact

Have questions or ideas, or just want to talk automation? Reach out. I enjoy sharing knowledge and learning from others.

> Still not married: currently dating bugs and fixing them one by one. 🐞

---

<p align="center">Feito com ☕ e muitos testes · Made with ☕ and lots of tests</p>
