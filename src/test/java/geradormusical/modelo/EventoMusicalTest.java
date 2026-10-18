package geradormusical.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EventoMusicalTest {

    @Test
    void deveCriarNotaComOsDadosInformados() {
        EventoMusical nota = EventoMusical.nota(16, 72, 100, 6);

        assertEquals(TipoEvento.NOTA, nota.tipo());
        assertEquals(16, nota.duracao());
        assertEquals(72, nota.notaMidi());
        assertEquals(100, nota.volume());
        assertEquals(6, nota.instrumento());
        assertEquals(0, nota.variacaoBpm());
    }

    @Test
    void deveCriarPausaComDuracaoPositiva() {
        EventoMusical pausa = EventoMusical.pausa(32);

        assertEquals(TipoEvento.PAUSA, pausa.tipo());
        assertEquals(32, pausa.duracao());
    }

    @Test
    void deveCriarAlteracaoParaAcelerarSemConsumirTempo() {
        EventoMusical alteracao = EventoMusical.alteracaoBpm(10);

        assertEquals(TipoEvento.ALTERACAO_BPM, alteracao.tipo());
        assertEquals(0, alteracao.duracao());
        assertEquals(10, alteracao.variacaoBpm());
    }

    @Test
    void deveAceitarVariacaoNegativaParaDesacelerar() {
        EventoMusical alteracao = EventoMusical.alteracaoBpm(-10);

        assertEquals(TipoEvento.ALTERACAO_BPM, alteracao.tipo());
        assertEquals(0, alteracao.duracao());
        assertEquals(-10, alteracao.variacaoBpm());
    }

    @Test
    void deveRejeitarTipoNulo() {
        assertThrows(IllegalArgumentException.class, () -> new EventoMusical(
                null, 16, 72, 100, 6, 0));
    }

    @Test
    void deveRejeitarNotaComDuracaoZeroOuNegativa() {
        assertThrows(IllegalArgumentException.class, () -> EventoMusical.nota(0, 72, 100, 6));
        assertThrows(IllegalArgumentException.class, () -> EventoMusical.nota(-1, 72, 100, 6));
    }

    @Test
    void deveRejeitarPausaComDuracaoZeroOuNegativa() {
        assertThrows(IllegalArgumentException.class, () -> EventoMusical.pausa(0));
        assertThrows(IllegalArgumentException.class, () -> EventoMusical.pausa(-1));
    }

    @Test
    void deveRejeitarAlteracaoBpmComDuracaoDiferenteDeZero() {
        assertThrows(IllegalArgumentException.class, () -> new EventoMusical(
                TipoEvento.ALTERACAO_BPM, 16, 0, 0, 0, 10));
        assertThrows(IllegalArgumentException.class, () -> new EventoMusical(
                TipoEvento.ALTERACAO_BPM, -1, 0, 0, 0, -10));
    }

    @Test
    void deveAceitarOsLimitesMinimosDeUmaNota() {
        EventoMusical nota = EventoMusical.nota(1, 0, 0, 0);

        assertEquals(1, nota.duracao());
        assertEquals(0, nota.notaMidi());
        assertEquals(0, nota.volume());
        assertEquals(0, nota.instrumento());
    }

    @Test
    void deveAceitarOsLimitesMaximosDosDadosMidi() {
        EventoMusical nota = EventoMusical.nota(16, 127, 127, 127);

        assertEquals(127, nota.notaMidi());
        assertEquals(127, nota.volume());
        assertEquals(127, nota.instrumento());
    }

    @Test
    void deveRejeitarNotaMidiForaDoIntervalo() {
        assertThrows(IllegalArgumentException.class, () -> EventoMusical.nota(16, -1, 100, 6));
        assertThrows(IllegalArgumentException.class, () -> EventoMusical.nota(16, 128, 100, 6));
    }

    @Test
    void deveRejeitarVolumeForaDoIntervalo() {
        assertThrows(IllegalArgumentException.class, () -> EventoMusical.nota(16, 72, -1, 6));
        assertThrows(IllegalArgumentException.class, () -> EventoMusical.nota(16, 72, 128, 6));
    }

    @Test
    void deveRejeitarInstrumentoForaDoIntervalo() {
        assertThrows(IllegalArgumentException.class, () -> EventoMusical.nota(16, 72, 100, -1));
        assertThrows(IllegalArgumentException.class, () -> EventoMusical.nota(16, 72, 100, 128));
    }
}
