package geradormusical.controle;

import geradormusical.modelo.Musica;
import javax.sound.midi.Sequence;


public class GeracaoMusical {
    private final Musica musica;
    private final Sequence sequencia;

    public GeracaoMusical(Musica musica, Sequence sequencia){
    if(musica == null || sequencia == null){
        throw new IllegalArgumentException("Música e sequência são obrigatórias.");
    }
    this.musica = musica;
    this.sequencia = sequencia;
}

public Musica getMusica(){
    return musica;
}

public Sequence getSequencia(){
    return sequencia;
}

}