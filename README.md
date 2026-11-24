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
│   ├── core/driver/                     # DriverFactory: criação do WebDriver
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
        └── suites/                      # smoke.xml, regression.xml
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
```

Também pode correr diretamente no IntelliJ IDEA: clique com o botão direito numa classe de teste e escolha **Run**.

### Roadmap

- [x] Estrutura base com Page Object Model
- [x] Page Objects e testes por módulo
- [x] Namespace único `mz.co.maximo.cis`, com o motor separado dos testes
- [x] Suites smoke e regressão
- [ ] Completar `DriverFactory` (vários navegadores, modo headless)
- [x] Ambientes configuráveis (QA / UAT / PROD)
- [ ] Relatórios de execução com screenshots em caso de falha
- [ ] Migração completa para Python (pytest + Selenium)
- [ ] Integração contínua (CI) com GitHub Actions

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
│   ├── core/driver/                     # DriverFactory: WebDriver creation
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
        └── suites/                      # smoke.xml, regression.xml
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
```

You can also run it straight from IntelliJ IDEA: right-click a test class and choose **Run**.

### Roadmap

- [x] Base structure with Page Object Model
- [x] Page Objects and tests per module
- [x] Single `mz.co.maximo.cis` namespace, engine separated from tests
- [x] Smoke and regression suites
- [ ] Complete `DriverFactory` (multiple browsers, headless mode)
- [x] Configurable environments (QA / UAT / PROD)
- [ ] Execution reports with screenshots on failure
- [ ] Full migration to Python (pytest + Selenium)
- [ ] Continuous integration (CI) with GitHub Actions

### Contact

Have questions or ideas, or just want to talk automation? Reach out. I enjoy sharing knowledge and learning from others.

> Still not married: currently dating bugs and fixing them one by one. 🐞

---

<p align="center">Feito com ☕ e muitos testes · Made with ☕ and lots of tests</p>
