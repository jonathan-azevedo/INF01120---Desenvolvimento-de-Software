package geradormusical.modelo;

public record EventoMusical(TipoEvento tipo, long duracao, int notaMidi, int volume,
                            int instrumento, int variacaoBpm) {

    public static final int NOTA_MIDI_MINIMA = 0;
    public static final int NOTA_MIDI_MAXIMA = 127;

    public EventoMusical {
        if (tipo == null) throw new IllegalArgumentException("Tipo de evento obrigatório.");
        if (tipo == TipoEvento.ALTERACAO_BPM) {
            if (duracao != 0) throw new IllegalArgumentException("Alteração de BPM não consome tempo.");
        } else if (duracao <= 0) {
            throw new IllegalArgumentException("Notas e pausas precisam de duração positiva.");
        }
        if (tipo == TipoEvento.NOTA) {
            ConfiguracaoMusical.validarIntervalo("Nota MIDI", notaMidi, NOTA_MIDI_MINIMA, NOTA_MIDI_MAXIMA);
            ConfiguracaoMusical.validarIntervalo("Volume", volume, ConfiguracaoMusical.VOLUME_MINIMO, ConfiguracaoMusical.VOLUME_MAXIMO);
            ConfiguracaoMusical.validarIntervalo("Instrumento", instrumento, ConfiguracaoMusical.INSTRUMENTO_MINIMO, ConfiguracaoMusical.INSTRUMENTO_MAXIMO);
        }
    }

    public static EventoMusical nota(long duracao, int notaMidi, int volume, int instrumento) {
        return new EventoMusical(TipoEvento.NOTA, duracao, notaMidi, volume, instrumento, 0);
    }
    public static EventoMusical pausa(long duracao) {
        return new EventoMusical(TipoEvento.PAUSA, duracao, 0, 0, 0, 0);
    }
    public static EventoMusical alteracaoBpm(int variacao) {
        return new EventoMusical(TipoEvento.ALTERACAO_BPM, 0, 0, 0, 0, variacao);
    }
}