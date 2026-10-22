package geradormusical.interpretacao.comandos;

import geradormusical.modelo.EstadoDaVoz;
import geradormusical.modelo.EventoMusical;

public interface ComandoMusical {
    // Retorna null quando o comando só muda o estado das próximas notas.
    EventoMusical executar(EstadoDaVoz estado);
}
