package geradormusical.interpretacao;

import geradormusical.interpretacao.comandos.ComandoTocarNota;
import geradormusical.modelo.*;

import java.util.ArrayList;
import java.util.List;

public final class InterpretadorTexto {
    private final DicionarioComandos dicionario;

    public InterpretadorTexto(DicionarioComandos dicionario) {
        this.dicionario = dicionario;
    }

    public Musica interpretar(String texto, ConfiguracaoMusical configuracao) {
        if (texto == null || texto.isBlank()) throw new IllegalArgumentException("Digite ou carregue um texto.");

        List<List<EventoMusical>> vozes = new ArrayList<>();
        String[] linhas = texto.replace("\uFEFF", "").split("\\R");

        for (int i = 0; i < linhas.length; i++) {
            if (linhas[i].isBlank()) continue;
            if (vozes.size() == ConfiguracaoMusical.QUANTIDADE_VOZES) {
                throw new IllegalArgumentException("use no máximo quatro linhas preenchidas (V0 a V3).");
            }
            vozes.add(processarLinha(linhas[i], i + 1, new EstadoDaVoz(vozes.size(), configuracao)));
        }

        while (vozes.size() < ConfiguracaoMusical.QUANTIDADE_VOZES) {
            vozes.add(List.of());
        }
        Musica musica = new Musica(configuracao.getBpm(), vozes);
        if (musica.ObterDuracaoTotal() == 0) {
            throw new IllegalArgumentException("O texto não produz notas nem pausas.");
        }
        return musica;
    }


    private List<EventoMusical> processarLinha(String linha, int numero, EstadoDaVoz estado) {
        List<EventoMusical> eventos = new ArrayList<>();
        int pos = 0;
        while (pos < linha.length() && Character.isWhitespace(linha.charAt(pos))) {
            pos++;
        }
        if (pos == 0 && linha.charAt(pos) == '[') {
            int fim = linha.indexOf(']', pos);
            if (fim < 0) throw new IllegalArgumentException("Linha " + numero + ", coluna " + (pos + 1) + ": atraso deve ser informado entre [ e ].");
            String valor = linha.substring(pos + 1, fim);
            if (!valor.matches("[0-9]+"))
                throw new IllegalArgumentException("Linha " + numero + ", coluna " + (pos + 1) + ": o atraso deve ser um inteiro não negativo.");
            int batidas = Integer.parseInt(valor);
            if (batidas > 0) {
                eventos.add(EventoMusical.pausa(batidas * 16));
            }
            pos = fim + 1;
        }

        while (pos < linha.length()) {
            char c = linha.charAt(pos);
            if (Character.isWhitespace(c)) {
                pos++;
                continue;
            }
            EventoMusical evento;
            int origem = pos;
            if (c >= 'A' && c <= 'G') {
                pos++;
                int acidente = 0; //Nota normal = 0 | # = 1 | b = -1
                if (pos < linha.length() && (linha.charAt(pos) == '#' || linha.charAt(pos) == 'b')) {
                    acidente = linha.charAt(pos) == '#' ? 1 : -1;
                    pos++;
                }
                if (pos + 1 >= linha.length() || linha.charAt(pos) != ':'
                        || linha.charAt(pos + 1) < '1' || linha.charAt(pos + 1) > '7') {
                    throw new IllegalArgumentException("Linha " + numero + ", coluna " + (origem + 1) + ": nota incompleta. Use A:3, A#:3 ou Ab:3, " + "com nota de A a G e duração de 1 a 7.");
                }
                evento = new ComandoTocarNota(c, acidente, TempoMusical.obterDuracao(linha.charAt(pos + 1) - '0')).executar(estado);
                pos += 2;
            } else {
                if (c == ':' || c == '#' || c == '[' || c == ']') {
                    throw new IllegalArgumentException("Linha " + numero + ", coluna " + (pos + 1) + ": símbolo '" + c + "' fora de uma nota ou atraso inicial. " + "Use A:3, A#:3 ou Ab:3;");
                }
                evento = dicionario.obterComando(c).executar(estado);
                pos++;
            }
            if (evento != null) eventos.add(evento);
        }
        return List.copyOf(eventos);
    }
}
