package geradormusical.audio;
import javax.sound.midi.Sequence;

public interface ReprodutorAudio {
    void carregar(Sequence sequencia);
    void iniciar();
    EstadoReproducao obterEstado();  
    void pausar();
    void retomar();
    void parar();
    void fechar();
}
