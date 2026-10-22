package geradormusical.interpretacao.comandos;

import geradormusical.modelo.EstadoDaVoz;
import geradormusical.modelo.EventoMusical;

public class ComandoAlterarBpm implements ComandoMusical {
    private final int variacao;

    public ComandoAlterarBpm(int variacao) {
        this.variacao = variacao;
    }

    @Override
    public EventoMusical executar(EstadoDaVoz estado) {
        estado.OutraInstrucao();
        return EventoMusical.alteracaoBpm(variacao);
    }
}
