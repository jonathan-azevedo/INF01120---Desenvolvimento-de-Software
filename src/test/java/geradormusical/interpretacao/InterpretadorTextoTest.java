package geradormusical.interpretacao;

import geradormusical.modelo.ConfiguracaoMusical;
import geradormusical.modelo.EventoMusical;
import geradormusical.modelo.Musica;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterpretadorTextoTest {
    private final InterpretadorTexto interpretador = new InterpretadorTexto(new DicionarioComandos());
    private final ConfiguracaoMusical configuracao = ConfiguracaoMusical.configuracaoPadrao();

    @Test
    void deveInterpretarNotasComAcidentesEDuracoesSemEspacos() {
        Musica musica = interpretador.interpretar("C:3D#:4Eb:2", configuracao);

        assertEquals(List.of(
                EventoMusical.nota(16, 72, 100, 6),
                EventoMusical.nota(8, 75, 100, 6),
                EventoMusical.nota(32, 75, 100, 6)), musica.obterEventos(0));
    }

    @Test
    void deveAplicarAConfiguracaoInformadaAsQuatroVozes() {
        ConfiguracaoMusical personalizada = new ConfiguracaoMusical(90,
                new int[]{1, 2, 3, 4}, new int[]{3, 4, 5, 6}, new int[]{10, 20, 30, 40});
        Musica musica = interpretador.interpretar("C:3\nC:3\nC:3\nC:3", personalizada);

        assertEquals(90, musica.getBPM());
        assertEquals(90, musica.getAndamentos().get(0L));
        for (int voz = 0; voz < ConfiguracaoMusical.QUANTIDADE_VOZES; voz++) {
            assertEquals(1, musica.obterEventos(voz).size());
            EventoMusical nota = musica.obterEventos(voz).get(0);
            assertEquals(12 * personalizada.getOitava(voz), nota.notaMidi());
            assertEquals(personalizada.getVolume(voz), nota.volume());
            assertEquals(personalizada.getInstrumento(voz), nota.instrumento());
        }
    }

    @Test
    void deveIgnorarBomELinhasVaziasECompletarVozesAusentes() {
        Musica musica = interpretador.interpretar("\uFEFFC:3\r\n\r\n \t\r\nD:3\r\n", configuracao);

        assertEquals(72, musica.obterEventos(0).get(0).notaMidi());
        assertEquals(62, musica.obterEventos(1).get(0).notaMidi());
        assertTrue(musica.obterEventos(2).isEmpty());
        assertTrue(musica.obterEventos(3).isEmpty());
    }

    @Test
    void espacosETabulacoesNaoDevemAlterarVolumeNemHistorico() {
        Musica semEspacos = interpretador.interpretar("C:4HH", configuracao);
        Musica comEspacos = interpretador.interpretar("  C:4 \tH H  ", configuracao);

        assertEquals(semEspacos.obterEventos(0), comEspacos.obterEventos(0));
        assertEquals(List.of(
                EventoMusical.nota(8, 72, 100, 6),
                EventoMusical.nota(8, 72, 100, 6),
                EventoMusical.pausa(16)), comEspacos.obterEventos(0));
    }

    @Test
    void comandosDeInstrumentoEOitavaNaoDevemCriarEventosNemAfetarOutraVoz() {
        Musica musica = interpretador.interpretar("!?C:3 V2 D:4\nC:3", configuracao);

        assertEquals(List.of(
                EventoMusical.nota(16, 84, 100, 24),
                EventoMusical.nota(8, 74, 100, 26)), musica.obterEventos(0));
        assertEquals(List.of(EventoMusical.nota(16, 60, 80, 20)), musica.obterEventos(1));
    }

    @Test
    void deveAceitarUmaLinhaComSomentePausa() {
        Musica musica = interpretador.interpretar("a", configuracao);

        assertEquals(List.of(EventoMusical.pausa(16)), musica.obterEventos(0));
        assertEquals(16, musica.ObterDuracaoTotal());
    }

    @Test
    void deveConverterAtrasoInicialEmPausaEAceitarAtrasoZero() {
        Musica musica = interpretador.interpretar("[2] C:3\n[0]D:4", configuracao);

        assertEquals(List.of(
                EventoMusical.pausa(32),
                EventoMusical.nota(16, 72, 100, 6)), musica.obterEventos(0));
        assertEquals(List.of(EventoMusical.nota(8, 62, 80, 20)), musica.obterEventos(1));
        assertEquals(48, musica.ObterDuracaoTotal());
    }

    @Test
    void deveRejeitarAtrasoForaDaPrimeiraPosicaoDaLinha() {
        String[] textos = {" [2] C:3", "\t[2]C:3", "C:3[2]D:3"};

        for (String texto : textos) {
            assertThrows(IllegalArgumentException.class,
                    () -> interpretador.interpretar(texto, configuracao));
        }
    }

    @Test
    void deveRejeitarTextoVazioComandosSemTempoENotacaoInvalida() {
        String[] textos = {
            null, "", " \n\t", ">?!;",
            "A", "A:0", "A:8", "A#", "C:3 #",
            "[]C:3", "[-1]C:3", "[1.5]C:3", "[a]C:3", "[2 C:3"
        };

        for (String texto : textos) {
            assertThrows(IllegalArgumentException.class,
                    () -> interpretador.interpretar(texto, configuracao));
        }
    }

    @Test
    void deveInformarLinhaEColunaNosErros() {
        IllegalArgumentException notaInvalida = assertThrows(IllegalArgumentException.class,
                () -> interpretador.interpretar("C:3\n\n  C#:8", configuracao));
        assertTrue(notaInvalida.getMessage().startsWith("Linha 3, coluna 3:"));

        IllegalArgumentException atrasoInvalido = assertThrows(IllegalArgumentException.class,
                () -> interpretador.interpretar("\n[2 C:3", configuracao));
        assertTrue(atrasoInvalido.getMessage().startsWith("Linha 2, coluna 1:"));
    }

    @Test
    void deveRejeitarMaisDeQuatroLinhasPreenchidas() {
        assertThrows(IllegalArgumentException.class,
                () -> interpretador.interpretar("C:3\n\nD:3\nE:3\nF:3\nG:3", configuracao));
    }

    @Test
    void deveAplicarMudancasGlobaisDeBpmNaOrdemDoTempoMusical() {
        Musica musica = interpretador.interpretar("[4]>C:3\n<C:3\n[2]>C:3", configuracao);

        assertEquals(new TreeMap<>(Map.of(0L, 110, 32L, 120, 64L, 130)), musica.getAndamentos());
    }
}
