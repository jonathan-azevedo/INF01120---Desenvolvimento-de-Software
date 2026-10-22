package geradormusical.interpretacao.comandos;

import geradormusical.modelo.EstadoDaVoz;
import geradormusical.modelo.EventoMusical;

public class ComandoTrocarInstrumento implements ComandoMusical {
    private final int valor;
    private final boolean somarAoAtual;

    public ComandoTrocarInstrumento(int valor, boolean somarAoAtual) {
        this.valor = valor;
        this.somarAoAtual = somarAoAtual;
    }

    @Override
    public EventoMusical executar(EstadoDaVoz estado) {
        int novoInstrumento = valor;
        if (somarAoAtual) {
            novoInstrumento = estado.getInstrumentoAtual() + valor;
        }

        // Os programas MIDI vão de 0 a 127.
        if (novoInstrumento < 0) {
            novoInstrumento = 0;
        } else if (novoInstrumento > 127) {
            novoInstrumento = 127;
        }

        estado.AlterarInstrumento(novoInstrumento);
        estado.OutraInstrucao();
        return null;
    }
}
