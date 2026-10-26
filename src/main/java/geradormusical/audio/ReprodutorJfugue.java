package geradormusical.audio;
import javax.sound.midi.Sequence;
import org.jfugue.player.ManagedPlayer;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;


public class ReprodutorJfugue implements ReprodutorAudio {
    private Sequence sequencia;
    private EstadoReproducao estado = EstadoReproducao.VAZIO;
    private ManagedPlayer reprodutor;

    @Override 
    public EstadoReproducao obterEstado() {
        if(estado == EstadoReproducao.REPRODUZINDO){
            if(reprodutor.isFinished()){
                estado = EstadoReproducao.FINALIZADO;
            }
        }
        return estado;
    }
    
    @Override 
    public void carregar(Sequence sequencia){
        verificarAberto();
        if(sequencia == null){
            throw new IllegalArgumentException("Sequencia nula");
        }

        parar();
        this.sequencia = sequencia;
        estado = EstadoReproducao.PRONTO;
    }

    @Override 
    public void iniciar(){
        verificarAberto();
        if (sequencia == null){
            throw new IllegalStateException("Carregue uma sequência antes de iniciar a reprodução.");
        }
        try{
            if(reprodutor == null){
                reprodutor = new ManagedPlayer();
            }
            reprodutor.reset();
            reprodutor.start(sequencia);
            estado = EstadoReproducao.REPRODUZINDO;
        }
        catch(InvalidMidiDataException erro){
            throw new IllegalStateException("Sequencia MIDI invalida", erro);
        }
        catch(MidiUnavailableException erro){
            throw new IllegalStateException("Dispositivo MIDI indisponivel", erro);
        }
    }

    @Override 
    public void pausar(){
        verificarAberto();
        if (obterEstado() == EstadoReproducao.REPRODUZINDO){
            reprodutor.pause();
            estado = EstadoReproducao.PAUSADO;
        }
    }

    @Override
    public void retomar(){
        verificarAberto();
        if(estado == EstadoReproducao.PAUSADO){
            reprodutor.resume();
            estado = EstadoReproducao.REPRODUZINDO;
        }
    }

    @Override 
    public void parar(){
        verificarAberto();
        if(reprodutor != null){
            reprodutor.reset();
        }

        if(sequencia == null){
            estado = EstadoReproducao.VAZIO;
        }
        else{
            estado = EstadoReproducao.PRONTO;
        }
    }

    @Override 
    public void fechar(){
        if(estado == EstadoReproducao.FECHADO){
            return;
        }
        parar();
        sequencia = null;
        estado = EstadoReproducao.FECHADO;
    }

    private void verificarAberto(){
        if(estado == EstadoReproducao.FECHADO){
            throw new IllegalStateException("Reprodutor encerrado");
        }
    }
    
}
