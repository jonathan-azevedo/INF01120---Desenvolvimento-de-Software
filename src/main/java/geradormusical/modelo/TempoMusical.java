package geradormusical.modelo;

public final class TempoMusical {

    public static final int UNIDADES_POR_BATIDA = 16;

    private TempoMusical() {}

    public static int obterDuracao(int codigo) {
        switch (codigo) {
            case 1:
                return UNIDADES_POR_BATIDA * 4;  // Semibreve
            case 2:
                return UNIDADES_POR_BATIDA * 2;  // Mínima
            case 3:
                return UNIDADES_POR_BATIDA;      // Semínima
            case 4:
                return UNIDADES_POR_BATIDA / 2;  // Colcheia
            case 5:
                return UNIDADES_POR_BATIDA / 4;  // Semicolcheia
            case 6:
                return UNIDADES_POR_BATIDA / 8;  // Fusa
            case 7:
                return UNIDADES_POR_BATIDA / 16; // Semifusa
            default:
                throw new IllegalArgumentException("Código de duração deve estar entre 1 e 7.");
        }
    }
}