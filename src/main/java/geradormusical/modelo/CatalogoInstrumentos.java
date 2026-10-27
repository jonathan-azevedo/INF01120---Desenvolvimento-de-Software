package geradormusical.modelo;

import java.util.ArrayList;
import java.util.List;

public final class CatalogoInstrumentos {
    public record Instrumento(int numeroPrograma, String nome) {
        @Override
        public String toString() {
            return String.format("%03d - %s", numeroPrograma, nome);
        }
    }

    private static final String[] NOMES = {
        "Piano acústico", "Piano brilhante", "Piano elétrico de cauda", "Piano honky-tonk",
        "Piano elétrico 1", "Piano elétrico 2", "Cravo", "Clavinete",
        "Celesta", "Glockenspiel", "Caixa de música", "Vibrafone",
        "Marimba", "Xilofone", "Sinos tubulares", "Dulcimer",
        "Órgão de barras", "Órgão percussivo", "Órgão rock", "Órgão de igreja",
        "Órgão de palhetas", "Acordeão", "Harmônica", "Acordeão tango",
        "Violão de nylon", "Violão de aço", "Guitarra jazz", "Guitarra limpa",
        "Guitarra abafada", "Guitarra com overdrive", "Guitarra distorcida", "Harmônicos de guitarra",
        "Baixo acústico", "Baixo elétrico finger", "Baixo elétrico palheta", "Baixo fretless",
        "Baixo slap 1", "Baixo slap 2", "Baixo sintetizado 1", "Baixo sintetizado 2",
        "Violino", "Viola", "Violoncelo", "Contrabaixo",
        "Cordas com tremolo", "Cordas pizzicato", "Harpa", "Tímpanos",
        "Conjunto de cordas 1", "Conjunto de cordas 2", "Cordas sintetizadas 1", "Cordas sintetizadas 2",
        "Coro aah", "Coro ooh", "Voz sintetizada", "Ataque orquestral",
        "Trompete", "Trombone", "Tuba", "Trompete com surdina",
        "Trompa", "Metais", "Metais sintetizados 1", "Metais sintetizados 2",
        "Sax soprano", "Sax alto", "Sax tenor", "Sax barítono",
        "Oboé", "Corne inglês", "Fagote", "Clarinete",
        "Flautim", "Flauta", "Flauta doce", "Flauta de pã",
        "Garrafa soprada", "Shakuhachi", "Apito", "Ocarina",
        "Lead quadrado", "Lead dente de serra", "Lead calliope", "Lead chiff",
        "Lead charang", "Lead voz", "Lead quintas", "Lead baixo e solo",
        "Pad fantasia", "Pad quente", "Pad polissynth", "Pad coro",
        "Pad cordas", "Pad metal", "Pad halo", "Pad sweep",
        "Efeito chuva", "Efeito trilha sonora", "Efeito cristal", "Efeito atmosfera",
        "Efeito brilho", "Efeito goblins", "Efeito ecos", "Efeito ficção científica",
        "Sitar", "Banjo", "Shamisen", "Koto",
        "Kalimba", "Gaita de foles", "Rabeca", "Shanai",
        "Sino", "Agogô", "Tambor de aço", "Bloco sonoro",
        "Taiko", "Tom melódico", "Tambor sintetizado", "Prato reverso",
        "Ruído de corda", "Ruído de sopro", "Onda do mar", "Pássaro",
        "Telefone", "Helicóptero", "Aplausos", "Tiro"
    };

    private final List<Instrumento> instrumentos;

    public CatalogoInstrumentos() {
        List<Instrumento> lista = new ArrayList<>();
        for (int numeroPrograma = 0; numeroPrograma < NOMES.length; numeroPrograma++) {
            lista.add(new Instrumento(numeroPrograma, NOMES[numeroPrograma]));
        }
        instrumentos = List.copyOf(lista);
    }

    public List<Instrumento> obterInstrumentos() {
        return instrumentos;
    }

    public Instrumento obterPorNumeroPrograma(int numeroPrograma) {
        return instrumentos.get(numeroPrograma);
    }
}