# Gerador Musical

Trabalho prático da disciplina INF01120 — Desenvolvimento de Software. A aplicação transforma texto em música com até quatro vozes simultâneas, utilizando Java, Swing e JFugue.

## Integrantes

- Jonathan Azevedo
- Lucas Kliemann
- Lucas Reinelli
- Gustavo Giordani

## Requisitos

- JDK 25 instalado.
- Git instalado para clonar o repositório.
- Acesso à internet na primeira execução do Gradle, para baixar a distribuição e as dependências.

## Clonar o repositório

Abra um terminal na pasta onde deseja baixar o projeto e execute:

```powershell
git clone https://github.com/jonathan-azevedo/INF01120---Desenvolvimento-de-Software.git
cd INF01120---Desenvolvimento-de-Software
```

## Compilar e executar

Com o terminal aberto na pasta do projeto, onde está o arquivo `gradlew.bat`. Execute:

```powershell
# Compilar, executar os testes e gerar a distribuição
.\gradlew.bat build

# Abrir a aplicação
.\gradlew.bat run
```

Para executar apenas os testes:

```powershell
.\gradlew.bat test
```

## Como usar

1. Digite o texto ou carregue um arquivo pelo botão **Abrir TXT**.
2. É possível alterar instrumento, volume e oitava de cada uma das vozes. O BPM é global.
3. Clique em **Gerar música**. A geração não inicia a reprodução automaticamente.
4. Use **Reproduzir**, **Pausar**, **Retomar** e **Parar**.
5. Use **Salvar TXT** para escolher o nome e o destino do texto.
6. Use **Exportar MIDI** para salvar a última música gerada.

Depois de alterar o texto ou os parâmetros, clique novamente em **Gerar música** para aplicar as mudanças. A exportação salva a última geração, não converte novamente o texto da tela.

## Regras básicas de mapeamento

### Notas e durações

As letras maiúsculas representam notas:

| Letra | Nota |
|---|---|
| A | Lá |
| B | Si |
| C | Dó |
| D | Ré |
| E | Mi |
| F | Fá |
| G | Sol |

Toda nota deve conter `:` seguido de um código de duração de 1 a 7. Exemplos: `C:3`, `A#:4` e `Bb:2`.

O sustenido (`#`) ou bemol (`b` minúsculo) aparece imediatamente depois da letra e antes de `:`. Letras, acidentes e duração de uma mesma nota não devem ser separados por espaços.

| Código | Figura musical | Duração em batidas |
|---|---|---|
| 1 | Semibreve | 4 |
| 2 | Mínima | 2 |
| 3 | Semínima | 1 |
| 4 | Colcheia | 1/2 |
| 5 | Semicolcheia | 1/4 |
| 6 | Fusa | 1/8 |
| 7 | Semifusa | 1/16 |

Neste projeto, uma batida corresponde à semínima.

### Vozes e atraso inicial

- Cada linha preenchida representa uma voz independente, tocada simultaneamente às demais.
- São permitidas até quatro linhas preenchidas, correspondentes às vozes 0 a 3. Linhas em branco são ignoradas.
- Para atrasar uma voz, coloque `[n]` no início da linha, onde `n` é um número inteiro não negativo de batidas. Exemplo: `[4] C:3 D:3`.
- O `[` deve ser o primeiro caractere da linha, sem espaços antes dele.
- Sem indicação de atraso, a voz começa imediatamente.

### Outros comandos

| Comando | Efeito |
|---|---|
| Espaço ou tabulação | Apenas separação visual;|
| Letras minúsculas de `a` a `h`, fora de uma nota | Pausa de uma batida |
| `?` ou `.` | Sobe uma oitava |
| `V` | Desce uma oitava |
| `>` | Aumenta o BPM global em 10 |
| `<` | Diminui o BPM global em 10 |
| `!` | Seleciona o instrumento de número 24 |
| `;` ou dígito ímpar isolado | Seleciona o instrumento de número 15 |
| `,` | Seleciona o instrumento de número 114 |
| Dígito par isolado | Soma seu valor ao número do instrumento atual, limitado a 127 |

O BPM é limitado ao intervalo de 10 a 300. Ao ultrapassar o intervalo de oitavas de 0 a 9, a voz retorna à sua oitava base.

Outros caracteres, como `H`, `O`, `I` e `U`, repetem a última nota se a instrução anterior tiver sido uma nota explícita; caso contrário, produzem uma pausa de uma batida. A repetição mantém os parâmetros e a duração da nota original, mas não permite uma cadeia de repetições: em `C:3HH`, o primeiro `H` repete e o segundo pausa.

Os símbolos `:`, `#`, `[` e `]` fora de uma nota ou atraso válido geram erro de interpretação. `H` não representa uma nota musical.

### Configuração inicial

O BPM inicial é 120. Os parâmetros padrão por voz são:

| Voz | Oitava | Volume | Número do instrumento |
|---|---|---|---|
| 0 | 6 | 100 | 6 |
| 1 | 5 | 80 | 20 |
| 2 | 4 | 60 | 0 |
| 3 | 3 | 40 | 70 |

O catálogo utiliza a numeração MIDI de 0 a 127. O botão **Restaurar Padrões** restaura os parâmetros das quatro vozes e o BPM global.

## Exemplo de texto

Cole o texto abaixo na aplicação. A segunda voz começa duas batidas depois da primeira:

```text
C:3 D:3 E:3 F:3 G:2 G:2
[2] C:2 E:2 G:1
```


