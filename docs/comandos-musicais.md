# Comandos musicais

Os comandos foram adaptados de `Gerador_musical_trabalho` para as classes já existentes neste projeto. A implementação usa classes comuns, construtores, `if` e `switch` tradicional.

## Como funciona

A interface `ComandoMusical` define o método `executar`. Cada classe implementa uma ação. O `EstadoDaVoz` guarda instrumento, volume, oitava e última nota da voz que está sendo processada.

O método `executar` retorna um `EventoMusical` quando produz nota, pausa ou alteração de BPM. Retorna `null` quando apenas modifica o instrumento ou a oitava das próximas notas.

Foi mantida a escrita dos métodos existentes em `EstadoDaVoz`, como `GuardarNota` e `VariarOitava`, para compatibilidade com o código e os testes atuais.

| Classe | Responsabilidade |
| --- | --- |
| `ComandoTocarNota` | Calcula a nota MIDI, cria o evento e guarda a nota no estado. |
| `ComandoPausa` | Cria uma pausa com a duração recebida. |
| `ComandoRepetirOuPausar` | Repete a última nota se a instrução anterior foi uma nota explícita; caso contrário, cria uma pausa de uma batida. |
| `ComandoAlterarOitava` | Soma ou subtrai oitavas; fora de 0–9, volta à oitava inicial. |
| `ComandoTrocarInstrumento` | Define ou incrementa o instrumento, mantendo o resultado entre 0 e 127. |
| `ComandoAlterarBpm` | Cria um evento com a variação de BPM. |

## Símbolos isolados

`DicionarioComandos.obterComando` escolhe a ação para cada símbolo:

| Símbolo | Efeito |
| --- | --- |
| `a` até `h` | Pausa de uma batida. |
| `?` ou `.` | Sobe uma oitava. |
| `V` | Desce uma oitava. |
| `>` ou `<` | Gera variação de BPM de +10 ou -10. |
| `!` | Instrumento 24. |
| `;` ou dígito ímpar | Instrumento 15. |
| `,` | Instrumento 114. |
| Dígito par | Soma o dígito ao instrumento atual. |
| Outros caracteres | Repetição ou pausa. |

## Exemplo de uso em Java

```java
ConfiguracaoMusical config = ConfiguracaoMusical.configuracaoPadrao();
EstadoDaVoz estado = new EstadoDaVoz(0, config);
DicionarioComandos dicionario = new DicionarioComandos();

// Dó natural, com duração de uma semínima.
int duracao = TempoMusical.obterDuracao(3);
ComandoMusical tocar = new ComandoTocarNota('C', 0, duracao);
EventoMusical nota = tocar.executar(estado);

ComandoMusical repetir = dicionario.obterComando('H');
EventoMusical repeticao = repetir.executar(estado);
EventoMusical pausa = repetir.executar(estado);

// Oitava e instrumento mudam o estado e retornam null.
ComandoMusical subirOitava = dicionario.obterComando('?');
subirOitava.executar(estado);
```

Imports usados no exemplo:

```java
import geradormusical.modelo.ConfiguracaoMusical;
import geradormusical.modelo.EstadoDaVoz;
import geradormusical.modelo.EventoMusical;
import geradormusical.modelo.TempoMusical;
import geradormusical.interpretacao.DicionarioComandos;
import geradormusical.interpretacao.comandos.ComandoMusical;
import geradormusical.interpretacao.comandos.ComandoTocarNota;
```

## Integração com as próximas etapas

Esta entrega contém a parte dos comandos e o dicionário de símbolos. O programa principal continua com o exemplo de reprodução JFugue que já existia.

O interpretador de texto ainda precisa ser implementado para ler notas como `C#:3`, durações e linhas. Ele deve criar `ComandoTocarNota` para as notas completas, ignorar espaços e consultar o dicionário somente para símbolos isolados. Letras, acidentes e dígitos de uma nota completa não devem ser processados novamente como comandos.

Ao construir a lista de eventos de uma voz, adicione apenas os resultados diferentes de `null`. As variações de BPM precisam ser aplicadas posteriormente pela etapa que monta a música; o comando somente registra a variação.

Uma batida corresponde a `TempoMusical.UNIDADES_POR_BATIDA` (16 unidades). O cálculo das notas segue o original: `12 * oitava + semitom + acidente`, com acidente 0 para natural, 1 para sustenido e -1 para bemol.

## Verificação

Os testes ficam em `src/test/java/geradormusical/interpretacao/ComandosMusicaisTest.java`. Cobrem notas naturais e acidentes, notas inválidas, repetição, pausas, oitavas, instrumentos, variações de BPM e independência entre vozes.

Execute `./gradlew test` em um ambiente com o JDK 24 solicitado no `build.gradle`. Nesta adaptação, os 40 testes (33 existentes e 7 novos) passaram usando JDK 25, compilação com `--release 24` e o executor JUnit disponível no computador. O `build.gradle` original foi preservado.
