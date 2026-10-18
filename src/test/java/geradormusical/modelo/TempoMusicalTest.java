package geradormusical.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TempoMusicalTest {

    @Test
    void deveConverterOsSeteCodigosParaUnidadesMusicais() {
        assertEquals(64, TempoMusical.obterDuracao(1)); // Semibreve
        assertEquals(32, TempoMusical.obterDuracao(2)); // Mínima
        assertEquals(16, TempoMusical.obterDuracao(3)); // Semínima
        assertEquals(8, TempoMusical.obterDuracao(4));  // Colcheia
        assertEquals(4, TempoMusical.obterDuracao(5));  // Semicolcheia
        assertEquals(2, TempoMusical.obterDuracao(6));  // Fusa
        assertEquals(1, TempoMusical.obterDuracao(7));  // Semifusa
    }

    @Test
    void deveRejeitarCodigoAbaixoDoMinimo() {
        assertThrows(IllegalArgumentException.class, () -> TempoMusical.obterDuracao(0));
        assertThrows(IllegalArgumentException.class, () -> TempoMusical.obterDuracao(-1));
    }

    @Test
    void deveRejeitarCodigoAcimaDoMaximo() {
        assertThrows(IllegalArgumentException.class, () -> TempoMusical.obterDuracao(8));
    }
}
