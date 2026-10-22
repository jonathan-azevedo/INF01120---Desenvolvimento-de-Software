package geradormusical.interpretacao;

import geradormusical.interpretacao.comandos.*;
import geradormusical.modelo.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ComandosMusicaisTest {
    @Test
    void deveTocarTodasAsNotasComAcidentesEDuracoes() {
        EstadoDaVoz estado = new EstadoDaVoz(80, 20, 5);
        char[] notas = {'C', 'D', 'E', 'F', 'G', 'A', 'B'};
        int[] semitons = {0, 2, 4, 5, 7, 9, 11};
        for (int i = 0; i < notas.length; i++) {
            for (int acidente = -1; acidente <= 1; acidente++) {
                EventoMusical evento = new ComandoTocarNota(notas[i], acidente, 8).executar(estado);
                assertEquals(60 + semitons[i] + acidente, evento.notaMidi());
                assertEquals(8, evento.duracao());
                assertEquals(80, evento.volume());
                assertEquals(20, evento.instrumento());
                assertEquals(evento, estado.getUltimaNota());
            }
        }
    }

    @Test
    void deveRejeitarNotaInvalidaSemMudarHistorico() {
        EstadoDaVoz estado = new EstadoDaVoz(80, 20, 0);
        assertThrows(IllegalArgumentException.class,
                () -> new ComandoTocarNota('H', 0, 16).executar(estado));
        assertThrows(IllegalArgumentException.class,
                () -> new ComandoTocarNota('C', -1, 16).executar(estado));
        assertThrows(IllegalArgumentException.class,
                () -> new ComandoTocarNota('C', 0, 0).executar(estado));
        assertFalse(estado.podeRepetirNota());
    }

    @Test
    void deveRepetirUmaVezEPausarNaSegundaTentativa() {
        EstadoDaVoz estado = new EstadoDaVoz(80, 20, 5);
        ComandoMusical repetir = new ComandoRepetirOuPausar();
        assertEquals(TipoEvento.PAUSA, repetir.executar(estado).tipo());
        EventoMusical nota = new ComandoTocarNota('A', 1, 8).executar(estado);
        assertEquals(nota, repetir.executar(estado));
        EventoMusical pausa = repetir.executar(estado);
        assertEquals(TipoEvento.PAUSA, pausa.tipo());
        assertEquals(16, pausa.duracao());
    }

    @Test
    void outrosComandosDevemInterromperRepeticao() {
        ComandoMusical[] comandos = {
            new ComandoPausa(16), new ComandoAlterarBpm(10),
            new ComandoAlterarOitava(1), new ComandoTrocarInstrumento(24, false)
        };
        for (ComandoMusical comando : comandos) {
            EstadoDaVoz estado = new EstadoDaVoz(80, 20, 5);
            new ComandoTocarNota('C', 0, 16).executar(estado);
            comando.executar(estado);
            assertFalse(estado.podeRepetirNota());
        }
    }

    @Test
    void deveAplicarSimbolosDeOitavaEVoltarABaseNosLimites() {
        DicionarioComandos dicionario = new DicionarioComandos();
        EstadoDaVoz estado = new EstadoDaVoz(80, 20, 8);
        assertNull(dicionario.obterComando('?').executar(estado));
        assertEquals(9, estado.getOitavaAtual());
        dicionario.obterComando('.').executar(estado);
        assertEquals(8, estado.getOitavaAtual());
        estado.AlterarOitava(0);
        dicionario.obterComando('V').executar(estado);
        assertEquals(8, estado.getOitavaAtual());
    }

    @Test
    void deveAplicarTodosOsSimbolosDeInstrumento() {
        DicionarioComandos dicionario = new DicionarioComandos();
        EstadoDaVoz estado = new EstadoDaVoz(80, 20, 5);
        char[] simbolos = {'!', ';', ','};
        int[] instrumentos = {24, 15, 114};
        for (int i = 0; i < simbolos.length; i++) {
            assertNull(dicionario.obterComando(simbolos[i]).executar(estado));
            assertEquals(instrumentos[i], estado.getInstrumentoAtual());
        }
        for (char digito = '0'; digito <= '9'; digito++) {
            estado.AlterarInstrumento(20);
            dicionario.obterComando(digito).executar(estado);
            int valor = digito - '0';
            if (valor % 2 == 0) {
                assertEquals(20 + valor, estado.getInstrumentoAtual());
            } else {
                assertEquals(15, estado.getInstrumentoAtual());
            }
        }
        estado.AlterarInstrumento(126);
        dicionario.obterComando('8').executar(estado);
        assertEquals(127, estado.getInstrumentoAtual());
    }

    @Test
    void deveProduzirPausasEVariaçõesDeBpmSemAfetarOutraVoz() {
        DicionarioComandos dicionario = DicionarioComandos.padrao();
        EstadoDaVoz estado = new EstadoDaVoz(80, 20, 5);
        EstadoDaVoz outraVoz = new EstadoDaVoz(1, ConfiguracaoMusical.configuracaoPadrao());
        for (char letra = 'a'; letra <= 'h'; letra++) {
            EventoMusical pausa = dicionario.obterComando(letra).executar(estado);
            assertEquals(TipoEvento.PAUSA, pausa.tipo());
            assertEquals(16, pausa.duracao());
        }
        EventoMusical acelerar = dicionario.obterComando('>').executar(estado);
        assertEquals(TipoEvento.ALTERACAO_BPM, acelerar.tipo());
        assertEquals(10, acelerar.variacaoBpm());
        assertEquals(0, acelerar.duracao());
        assertEquals(-10, dicionario.obterComando('<').executar(estado).variacaoBpm());
        dicionario.obterComando('!').executar(estado);
        dicionario.obterComando('?').executar(estado);
        assertEquals(20, outraVoz.getInstrumentoAtual());
        assertEquals(5, outraVoz.getOitavaAtual());
    }
}
