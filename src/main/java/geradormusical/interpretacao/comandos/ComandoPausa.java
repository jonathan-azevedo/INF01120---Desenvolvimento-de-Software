package geradormusical.interpretacao.comandos;

import geradormusical.modelo.EstadoDaVoz;
import geradormusical.modelo.EventoMusical;

public class ComandoPausa implements ComandoMusical {
    private final long duracao;

    public ComandoPausa(long duracao) {
        this.duracao = duracao;
    }

    @Override
    public EventoMusical executar(EstadoDaVoz estado) {
        estado.OutraInstrucao();
        return EventoMusical.pausa(duracao);
    }
}
