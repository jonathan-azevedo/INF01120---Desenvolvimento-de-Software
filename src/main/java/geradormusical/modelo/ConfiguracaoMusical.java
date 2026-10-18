package geradormusical.modelo;

public class ConfiguracaoMusical {
    public static final int QUANTIDADE_VOZES = 4;
    public static final int BPM_MINIMO = 10;
    public static final int BPM_MAXIMO = 300;

    public static final int INSTRUMENTO_MINIMO = 0;
    public static final int INSTRUMENTO_MAXIMO = 127;

    public static final int VOLUME_MINIMO = 0;
    public static final int VOLUME_MAXIMO = 127;

    public static final int OITAVA_MINIMA = 0;
    public static final int OITAVA_MAXIMA = 9;

    private final int bpm;
    private final int[] instrumentos;
    private final int[] oitavas;
    private final int[] volumes;

    public ConfiguracaoMusical(int bpm, int[] instrumentos, int[] oitavas, int[] volumes) {
        validarIntervalo("Bpm", bpm, BPM_MINIMO, BPM_MAXIMO);
        this.bpm = bpm;
        this.instrumentos = validarVetor("Instrumento", instrumentos, INSTRUMENTO_MINIMO, INSTRUMENTO_MAXIMO);
        this.oitavas = validarVetor("Oitava", oitavas, OITAVA_MINIMA, OITAVA_MAXIMA);
        this.volumes = validarVetor("Volume", volumes, VOLUME_MINIMO, VOLUME_MAXIMO);
    }

     public static ConfiguracaoMusical configuracaoPadrao(){
        int bpm = 120;
        int[] instrumentos = {6, 20, 0, 70}; //Cravo, orgão, piano, fagote
        int[] oitavas = {6, 5, 4, 3};
        int[]  volumes = {100, 80, 60, 40};

        return new ConfiguracaoMusical(bpm, instrumentos, oitavas, volumes);
     }

     public int getBpm() {
        return bpm;
     }

     public int getInstrumento(int voz){
        validarVoz(voz);
        return instrumentos[voz];
     }

     public int getOitava(int voz){
        validarVoz(voz);
        return oitavas[voz];
     }

     public int getVolume(int voz){
        validarVoz(voz);
        return volumes[voz];
     }

    private static void validarVoz(int voz) {
        validarIntervalo("Voz", voz, 0, QUANTIDADE_VOZES - 1);
    }

    public static void validarIntervalo(String nome, int valor, int minimo, int maximo) {
        if (valor < minimo || valor > maximo) {
            throw new IllegalArgumentException(nome + " deve estar entre " + minimo + " e " + maximo + ". Recebido: " + valor + "."
            );
        }
    }

    private static int[] validarVetor(String nome, int[] vetor, int minimo, int maximo) {
        if (vetor == null || vetor.length != QUANTIDADE_VOZES) {
            throw new IllegalArgumentException(nome + " deve ter exatamente quatro valores.");
        }
        int[] copia = vetor.clone();
        for(int i : copia) {
            validarIntervalo(nome, i, minimo, maximo);
        }
        return copia;
    }
}
