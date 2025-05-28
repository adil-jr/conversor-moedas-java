# Conversor de Moedas Java

Este é um projeto de console em Java puro que permite converter valores entre diferentes moedas utilizando taxas de câmbio em tempo real da [ExchangeRate-API](https://www.exchangerate-api.com/). O projeto também mantém um histórico das conversões realizadas durante a sessão e registra todas as transações em um arquivo de log.

## Funcionalidades

* **Conversão de Moedas**: Converte valores entre 10 moedas diferentes com base em taxas de câmbio atualizadas.
* **Moedas Suportadas**:
    1.  USD (Dólar Americano)
    2.  EUR (Euro)
    3.  BRL (Real Brasileiro)
    4.  GBP (Libra Esterlina)
    5.  JPY (Iene Japonês)
    6.  CAD (Dólar Canadense)
    7.  AUD (Dólar Australiano)
    8.  CHF (Franco Suíço)
    9.  CNY (Yuan Chinês)
    10. MXN (Peso Mexicano)
* **Histórico de Conversões**: Mantém um histórico das últimas conversões realizadas (limitado às últimas 10, apenas durante a sessão atual).
* **Logs Persistentes**: Registra todas as conversões em um arquivo `conversion_logs.txt` com data, hora, moedas, valor, taxa e resultado.
* **Interface de Linha de Comando**: Interação com o usuário através do console.
* **Validação de Entrada**: Verifica se as entradas do usuário são válidas.
* **Tratamento de Erros**: Lida com possíveis erros de API, conexão e entrada de dados.

## Requisitos Técnicos

* **Linguagem**: Java (JDK 11 ou superior recomendado)
* **API Externa**: [ExchangeRate-API](https://www.exchangerate-api.com/)
* **Biblioteca JSON**: GSON (exemplo: `gson-2.13.1.jar` ou mais recente)
* **Variável de Ambiente para API Key**: `EXCHANGERATE_API_KEY`

## Configuração

### 1. API Key

Este projeto requer uma API Key da ExchangeRate-API. **Esta chave deve ser configurada como uma variável de ambiente.**

**Como configurar a variável de ambiente `EXCHANGERATE_API_KEY`:**

* **Linux/macOS:**
    Adicione a seguinte linha ao seu arquivo de perfil do shell (por exemplo, `.bashrc`, `.zshrc`):
    ```bash
    export EXCHANGERATE_API_KEY="suaApiKey"
    ```
    Depois, recarregue o perfil (ex: `source ~/.bashrc`) ou abra um novo terminal.

* **Windows:**
    1.  Pesquise por "variáveis de ambiente" na barra de pesquisa do Windows e selecione "Editar as variáveis de ambiente do sistema".
    2.  Na janela "Propriedades do Sistema", clique no botão "Variáveis de Ambiente...".
    3.  Em "Variáveis de usuário" ou "Variáveis do sistema", clique em "Novo...".
    4.  Nome da variável: `EXCHANGERATE_API_KEY`
    5.  Valor da variável: `suaApiKey`
    6.  Clique em OK em todas as janelas. Pode ser necessário reiniciar o CMD/PowerShell ou o seu IDE.

### 2. Biblioteca GSON

Você precisará da biblioteca GSON para parsing de JSON.
1.  Baixe o arquivo `.jar` do GSON (ex: `gson-2.13.1.jar`) do [Repositório Maven](https://mvnrepository.com/artifact/com.google.code.gson/gson) ou do site oficial.
2.  Crie um diretório `lib` na raiz do projeto (ao lado do diretório `src`).
3.  Coloque o arquivo `gson-VERSION.jar` dentro do diretório `lib`.

## Estrutura do Projeto
```
conversor-moedas/
├── .gitignore
├── README.md
├── lib/
│   └── gson-VERSION.jar  <-- Coloque o JAR do GSON aqui
└── src/
└── com/
└── github/
└── adiljr/
├── Main.java                   # Classe principal com interface do usuário
├── model/
│   ├── ConversionRecord.java   # Representa um registro de conversão
│   ├── Currency.java           # Enum para moedas suportadas
│   └── ExchangeRateApiResponse.java # Mapeia a resposta JSON da API
├── service/
│   ├── ApiService.java         # Comunicação com a ExchangeRate-API
│   ├── HistoryService.java     # Gerenciamento do histórico de conversões
│   └── LogService.java         # Sistema de logs
└── util/
├── ConfigUtil.java         # Carrega configurações (API Key)
└── InputUtil.java          # Utilitários para entrada e validação
```
## Compilando e Executando

1.  **Navegue até o diretório raiz do projeto** (`conversor-moedas`) pelo terminal.

2.  **Compile o projeto:**

    * **Linux/macOS:**
        ```bash
        javac -cp "lib/gson-VERSION.jar:src" -d out $(find src -name "*.java")
        ```
        (Substitua `gson-VERSION.jar` pelo nome exato do seu arquivo JAR do GSON, ex: `gson-2.13.1.jar`)

    * **Windows (CMD/PowerShell):**
        ```powershell
        javac -cp "lib\gson-VERSION.jar;src" -d out (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })
        ```
        ou, de forma mais simples se estiver no diretório `src`:
        ```powershell
        # Navegue para o diretório src
        # cd src
        # Crie o diretório out manualmente se não existir, no nível raiz do projeto
        # mkdir ..\out
        # javac -cp "..\lib\gson-VERSION.jar;." -d ..\out com\github\adiljr\*.java com\github\adiljr\model\*.java com\github\adiljr\service\*.java com\github\adiljr\util\*.java
        ```
        A forma mais garantida e simples para Windows, a partir da raiz do projeto:
        ```powershell
        # Certifique-se que o diretório 'out' existe na raiz do projeto, ou crie-o.
        # mkdir out
        javac -cp "lib\gson-VERSION.jar" -d out src/com/github/adiljr/Main.java src/com/github/adiljr/model/*.java src/com/github/adiljr/service/*.java src/com/github/adiljr/util/*.java
        ```
        (Substitua `gson-VERSION.jar` pelo nome exato do seu arquivo JAR do GSON)

3.  **Execute o programa:**

    * **Linux/macOS:**
        ```bash
        java -cp "lib/gson-VERSION.jar:out" br.com.conversor.Main
        ```

    * **Windows (CMD/PowerShell):**
        ```powershell
        java -cp "lib\gson-VERSION.jar;out" com.github.adiljr.Main
        ```

    (Novamente, substitua `gson-VERSION.jar` pelo nome correto do arquivo)

Após a execução, um arquivo `conversion_logs.txt` será criado (ou atualizado) na raiz do projeto com os registros de todas as conversões.

## Exemplo de Arquivo de Configuração de Variáveis de Ambiente

Não há um arquivo de configuração direto no projeto para a API Key, pois ela é lida de uma variável de ambiente do sistema (`EXCHANGERATE_API_KEY`). Certifique-se de que esta variável está configurada corretamente no seu sistema operacional conforme as instruções acima.

**Exemplo de como a variável deve ser definida (conceitualmente):**
`EXCHANGERATE_API_KEY="suaApiKey"`
