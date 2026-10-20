package geradormusical.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EstadoDaVozTest {

    @Test
    void deveUsarOsValoresIniciaisDaVoz() {
        ConfiguracaoMusical config = ConfiguracaoMusical.configuracaoPadrao();
        EstadoDaVoz estado = new EstadoDaVoz(0, config);

        assertEquals(config.getVolume(0), estado.getVolumeAtual());
        assertEquals(config.getInstrumento(0), estado.getInstrumentoAtual());
        assertEquals(config.getOitava(0), estado.getOitavaAtual());
    }

    @Test
    void deveRejeitarAlteracoesInvalidas() {
        EstadoDaVoz estado = new EstadoDaVoz(80, 20, 6);

        assertThrows(IllegalArgumentException.class, () -> estado.AlterarVolume(-1));
        assertThrows(IllegalArgumentException.class, () -> estado.AlterarInstrumento(128));
        assertThrows(IllegalArgumentException.class, () -> estado.AlterarOitava(10));

        assertEquals(80, estado.getVolumeAtual());
        assertEquals(20, estado.getInstrumentoAtual());
        assertEquals(6, estado.getOitavaAtual());
    }

    @Test
    void deveVariarOitavaEVoltarABaseAoUltrapassarLimite() {
        EstadoDaVoz estado = new EstadoDaVoz(80, 20, 6);

        estado.VariarOitava(1);
        assertEquals(7, estado.getOitavaAtual());

        estado.AlterarOitava(9);
        estado.VariarOitava(1);
        assertEquals(6, estado.getOitavaAtual());

        estado.AlterarOitava(0);
        estado.VariarOitava(-1);
        assertEquals(6, estado.getOitavaAtual());
    }

    @Test
    void deveGuardarNotaEControlarPermissaoDeRepeticao() {
        EstadoDaVoz estado = new EstadoDaVoz(80, 20, 6);
        EventoMusical nota = EventoMusical.nota(16, 60, 80, 20);

        assertEquals(false, estado.podeRepetirNota());

        estado.GuardarNota(nota);
        assertEquals(nota, estado.getUltimaNota());
        assertEquals(true, estado.podeRepetirNota());

        estado.OutraInstrucao();
        assertEquals(false, estado.podeRepetirNota());
        assertEquals(nota, estado.getUltimaNota());
    }
}