package geradormusical.interpretacao.comandos;

import geradormusical.modelo.EstadoDaVoz;
import geradormusical.modelo.EventoMusical;

public class ComandoAlterarOitava implements ComandoMusical {
    private final int deslocamento;

    public ComandoAlterarOitava(int deslocamento) {
        this.deslocamento = deslocamento;
    }

    @Override
    public EventoMusical executar(EstadoDaVoz estado) {
        estado.VariarOitava(deslocamento);
        estado.OutraInstrucao();
        return null;
    }
}
