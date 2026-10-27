package geradormusical.arquivo;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

public class GerenciadorArquivo {

    public String lerTexto(Path caminho) throws IOException {
        return Files.readString(caminho, StandardCharsets.UTF_8);
    }

    public void salvarTexto(String texto, Path caminho) throws IOException {
        Files.writeString(caminho, texto, StandardCharsets.UTF_8);
    }

    public void salvarMidi(Sequence sequencia, Path caminho) throws IOException {
        int[] tipos = MidiSystem.getMidiFileTypes(sequencia);
        if (tipos.length == 0) {
            throw new IOException("Não há um formato MIDI disponível para esta sequência.");
        }

        try (OutputStream arquivo = Files.newOutputStream(caminho)) {
            MidiSystem.write(sequencia, tipos[0], arquivo);
        }
    }
}