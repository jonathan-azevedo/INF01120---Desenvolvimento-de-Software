package geradormusical.interpretacao.comandos;

import geradormusical.modelo.EstadoDaVoz;
import geradormusical.modelo.EventoMusical;
import geradormusical.modelo.TempoMusical;

public class ComandoRepetirOuPausar implements ComandoMusical {
    @Override
    public EventoMusical executar(EstadoDaVoz estado) {
        EventoMusical evento;
        if (estado.podeRepetirNota()) {
            evento = estado.getUltimaNota();
        } else {
            evento = EventoMusical.pausa(TempoMusical.UNIDADES_POR_BATIDA);
        }

        // Repetir uma nota não permite repetir novamente em seguida.
        estado.OutraInstrucao();
        return evento;
    }
}
