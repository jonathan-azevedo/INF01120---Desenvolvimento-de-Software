package geradormusical.modelo;
import java.util.List;
import java.util.Collections;
import java.util.NavigableMap;
import java.util.TreeMap;


public class Musica {

    private final List<List<EventoMusical>> vozes;
    private final int bpm; 
    private final NavigableMap<Long, Integer> andamentos;


    public Musica(int bpm, List<List<EventoMusical>>vozes){

    if (bpm < ConfiguracaoMusical.BPM_MINIMO || bpm > ConfiguracaoMusical.BPM_MAXIMO){   
        throw new IllegalArgumentException("BPM deve estar entre 10 e 300");
    } 
    
    if (vozes == null || vozes.size()  != ConfiguracaoMusical.QUANTIDADE_VOZES){
        throw new IllegalArgumentException("Erro na seleção de vozes");
    }

  this.vozes = vozes;
  this.bpm = bpm;  
  this.andamentos = resolverAndamentos(bpm, vozes);
    
    }

    public int getBPM(){
        return bpm;
    }

    public NavigableMap<Long, Integer> getAndamentos(){
        return andamentos;
    }

    private static NavigableMap<Long, Integer> resolverAndamentos(
            int bpmInicial, List<List<EventoMusical>> vozes) {
        TreeMap<Long, Integer> variacoesPorPosicao = new TreeMap<>();

        for (List<EventoMusical> voz : vozes) {
            long posicao = 0;

            for (EventoMusical evento : voz) {
                if (evento.tipo() == TipoEvento.ALTERACAO_BPM) {
                    int variacao = evento.variacaoBpm();

                    if (variacoesPorPosicao.containsKey(posicao)) {
                        int variacaoExistente = variacoesPorPosicao.get(posicao);
                        variacoesPorPosicao.put(posicao, variacaoExistente + variacao);
                    } else {
                        variacoesPorPosicao.put(posicao, variacao);
                    }
                } else {
                    posicao += evento.duracao();
                }
            }
        }

        TreeMap<Long, Integer> resultado = new TreeMap<>();
        int bpmAtual = bpmInicial;
        resultado.put(0L, bpmAtual);

        for (Long posicao : variacoesPorPosicao.keySet()) {
            int variacao = variacoesPorPosicao.get(posicao);
            long novoBpm = (long) bpmAtual + variacao;

            if (novoBpm < ConfiguracaoMusical.BPM_MINIMO) {
                novoBpm = ConfiguracaoMusical.BPM_MINIMO;
            }
            if (novoBpm > ConfiguracaoMusical.BPM_MAXIMO) {
                novoBpm = ConfiguracaoMusical.BPM_MAXIMO;
            }

            bpmAtual = (int) novoBpm;
            resultado.put(posicao, bpmAtual);
        }

        return Collections.unmodifiableNavigableMap(resultado);
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
