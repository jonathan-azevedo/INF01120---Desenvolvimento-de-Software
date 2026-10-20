package geradormusical.modelo;
import java.util.List;


public class Musica {

    private final List<List<EventoMusical>> vozes;
    private final int bpm; 


    public Musica(int bpm, List<List<EventoMusical>>vozes){

    if (bpm < ConfiguracaoMusical.BPM_MINIMO || bpm > ConfiguracaoMusical.BPM_MAXIMO){   
        throw new IllegalArgumentException("BPM deve estar entre 10 e 300");
    } 
    
    if (vozes == null || vozes.size()  != ConfiguracaoMusical.QUANTIDADE_VOZES){
        throw new IllegalArgumentException("Erro na seleção de vozes");
    }

  this.vozes = vozes;
  this.bpm = bpm;  
    
    
    }

    public int getBPM(){
        return bpm;
    }

    public List<EventoMusical> obterEventos(int voz){ 
        if (voz < 0 || voz >= ConfiguracaoMusical.QUANTIDADE_VOZES){ 
            throw new IllegalArgumentException("Voz deve estar entre uma das disponíveis");
        }
    return vozes.get(voz);
    }

    public long ObterDuracaoTotal(){
        long maior_duracao = 0;
    
        // Percorre as vozes para calcular a duração de cada uma
        for (int voz = 0; voz < vozes.size();voz++){ 

            // Seleciona a lista de eventos da voz atual para somar seus tempos
            List<EventoMusical> eventos = vozes.get(voz);
            long duracao_voz = 0;
            
            // Percorre os eventos da voz selecionada para calcular a duração
            for(int i = 0; i < eventos.size();i++){
                EventoMusical evento = eventos.get(i);
                duracao_voz += evento.duracao();
            }
    
            if (duracao_voz > maior_duracao){
                maior_duracao = duracao_voz;
            }
        }
    
    return maior_duracao;

    }

}