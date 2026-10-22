package geradormusical.interpretacao.comandos;

import geradormusical.modelo.EstadoDaVoz;
import geradormusical.modelo.EventoMusical;

public class ComandoTocarNota implements ComandoMusical {
    private final char nota;
    private final int acidente;
    private final long duracao;

    public ComandoTocarNota(char nota, int acidente, long duracao) {
        this.nota = nota;
        this.acidente = acidente;
        this.duracao = duracao;
    }

    @Override
    public EventoMusical executar(EstadoDaVoz estado) {
        int semitom;
        switch (nota) {
            case 'C': semitom = 0; break;
            case 'D': semitom = 2; break;
            case 'E': semitom = 4; break;
            case 'F': semitom = 5; break;
            case 'G': semitom = 7; break;
            case 'A': semitom = 9; break;
            case 'B': semitom = 11; break;
            default:
                throw new IllegalArgumentException("Nota inválida: " + nota);
        }

        // Acidente: +1 para sustenido, -1 para bemol e 0 para nota natural.
        int notaMidi = 12 * estado.getOitavaAtual() + semitom + acidente;
        EventoMusical evento = EventoMusical.nota(duracao, notaMidi,
                estado.getVolumeAtual(), estado.getInstrumentoAtual());
        estado.GuardarNota(evento);
        return evento;
    }
}
