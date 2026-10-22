package geradormusical.modelo;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MusicaAndamentoTest {

    @Test
    void deveResolverMudancasDeBpmAoLongoDoTempo() {
        List<EventoMusical> voz1 = List.of(
                EventoMusical.nota(16, 60, 80, 0),
                EventoMusical.alteracaoBpm(10),
                EventoMusical.pausa(16),
                EventoMusical.alteracaoBpm(-10));
        List<EventoMusical> voz2 = List.of(
                EventoMusical.nota(32, 64, 80, 0),
                EventoMusical.alteracaoBpm(20));

        Musica musica = new Musica(120, List.of(voz1, voz2, List.of(), List.of()));

        assertEquals(new TreeMap<>(Map.of(0L, 120, 16L, 130, 32L, 140)),
                musica.getAndamentos());
    }

    @Test
    void deveManterBpmEntreOMinimoEOmaximo() {
        List<EventoMusical> aceleracoes = java.util.Collections.nCopies(
                30, EventoMusical.alteracaoBpm(10));
        List<EventoMusical> desaceleracoes = java.util.Collections.nCopies(
                30, EventoMusical.alteracaoBpm(-10));

        Musica musicaRapida = new Musica(120,
                List.of(aceleracoes, List.of(), List.of(), List.of()));
        Musica musicaLenta = new Musica(120,
                List.of(desaceleracoes, List.of(), List.of(), List.of()));

        assertEquals(ConfiguracaoMusical.BPM_MAXIMO, musicaRapida.getAndamentos().get(0L));
        assertEquals(ConfiguracaoMusical.BPM_MINIMO, musicaLenta.getAndamentos().get(0L));
    }

    @Test
    void naoDevePermitirAlterarOTempoResolvido() {
        Musica musica = new Musica(120, List.of(List.of(), List.of(), List.of(), List.of()));

        assertThrows(UnsupportedOperationException.class,
                () -> musica.getAndamentos().put(16L, 150));
    }
}
