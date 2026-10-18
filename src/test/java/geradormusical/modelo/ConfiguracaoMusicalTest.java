package geradormusical.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConfiguracaoMusicalTest {

    @Test
    void deveCriarConfiguracaoPadraoDasQuatroVozes() {
        ConfiguracaoMusical configuracao = ConfiguracaoMusical.configuracaoPadrao();

        int[] instrumentosEsperados = {6, 20, 0, 70};
        int[] oitavasEsperadas = {6, 5, 4, 3};
        int[] volumesEsperados = {100, 80, 60, 40};

        assertEquals(120, configuracao.getBpm());
        for (int voz = 0; voz < 4; voz++) {
            assertEquals(instrumentosEsperados[voz], configuracao.getInstrumento(voz));
            assertEquals(oitavasEsperadas[voz], configuracao.getOitava(voz));
            assertEquals(volumesEsperados[voz], configuracao.getVolume(voz));
        }
    }

    @Test
    void deveGuardarOsValoresInformadosParaCadaVoz() {
        int bpm = 90;
        int[] instrumentos = {10, 30, 50, 70};
        int[] oitavas = {3, 4, 5, 6};
        int[] volumes = {20, 40, 60, 80};

        ConfiguracaoMusical configuracao = new ConfiguracaoMusical(
                bpm, instrumentos, oitavas, volumes);

        assertEquals(bpm, configuracao.getBpm());
        for (int voz = 0; voz < 4; voz++) {
            assertEquals(instrumentos[voz], configuracao.getInstrumento(voz));
            assertEquals(oitavas[voz], configuracao.getOitava(voz));
            assertEquals(volumes[voz], configuracao.getVolume(voz));
        }
    }

    @Test
    void deveAceitarOsLimitesMinimos() {
        ConfiguracaoMusical configuracao = new ConfiguracaoMusical(
                10, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0});

        assertEquals(10, configuracao.getBpm());
        for (int voz = 0; voz < 4; voz++) {
            assertEquals(0, configuracao.getInstrumento(voz));
            assertEquals(0, configuracao.getOitava(voz));
            assertEquals(0, configuracao.getVolume(voz));
        }
    }

    @Test
    void deveAceitarOsLimitesMaximos() {
        ConfiguracaoMusical configuracao = new ConfiguracaoMusical(
                300, new int[]{127, 127, 127, 127}, new int[]{9, 9, 9, 9}, new int[]{127, 127, 127, 127});

        assertEquals(300, configuracao.getBpm());
        for (int voz = 0; voz < 4; voz++) {
            assertEquals(127, configuracao.getInstrumento(voz));
            assertEquals(9, configuracao.getOitava(voz));
            assertEquals(127, configuracao.getVolume(voz));
        }
    }

    @Test
    void deveRejeitarBpmAbaixoDoMinimo() {
        assertThrows(IllegalArgumentException.class, () -> new ConfiguracaoMusical(
                9, new int[4], new int[4], new int[4]));
    }

    @Test
    void deveRejeitarBpmAcimaDoMaximo() {
        assertThrows(IllegalArgumentException.class, () -> new ConfiguracaoMusical(
                301, new int[4], new int[4], new int[4]));
    }

    @Test
    void deveRejeitarVetoresNulos() {
        assertThrows(IllegalArgumentException.class, () -> new ConfiguracaoMusical(
                120, null, new int[4], new int[4]));
        assertThrows(IllegalArgumentException.class, () -> new ConfiguracaoMusical(
                120, new int[4], null, new int[4]));
        assertThrows(IllegalArgumentException.class, () -> new ConfiguracaoMusical(
                120, new int[4], new int[4], null));
    }

    @Test
    void deveRejeitarVetoresQueNaoTenhamQuatroPosicoes() {
        int[] tamanhosInvalidos = {0, 3, 5};

        for (int tamanho : tamanhosInvalidos) {
            assertThrows(IllegalArgumentException.class, () -> new ConfiguracaoMusical(
                    120, new int[tamanho], new int[4], new int[4]));
            assertThrows(IllegalArgumentException.class, () -> new ConfiguracaoMusical(
                    120, new int[4], new int[tamanho], new int[4]));
            assertThrows(IllegalArgumentException.class, () -> new ConfiguracaoMusical(
                    120, new int[4], new int[4], new int[tamanho]));
        }
    }

    @Test
    void deveRejeitarInstrumentosForaDoIntervalo() {
        int[] valoresInvalidos = {-1, 128};

        for (int valor : valoresInvalidos) {
            assertThrows(IllegalArgumentException.class, () -> new ConfiguracaoMusical(
                    120, new int[]{6, 20, 0, valor}, new int[4], new int[4]));
        }
    }

    @Test
    void deveRejeitarOitavasForaDoIntervalo() {
        int[] valoresInvalidos = {-1, 10};

        for (int valor : valoresInvalidos) {
            assertThrows(IllegalArgumentException.class, () -> new ConfiguracaoMusical(
                    120, new int[4], new int[]{6, 5, 4, valor}, new int[4]));
        }
    }

    @Test
    void deveRejeitarVolumesForaDoIntervalo() {
        int[] valoresInvalidos = {-1, 128};

        for (int valor : valoresInvalidos) {
            assertThrows(IllegalArgumentException.class, () -> new ConfiguracaoMusical(
                    120, new int[4], new int[4], new int[]{100, 80, 60, valor}));
        }
    }

    @Test
    void alterarVetoresOriginaisNaoDeveAlterarAConfiguracao() {
        int[] instrumentos = {6, 20, 0, 70};
        int[] oitavas = {6, 5, 4, 3};
        int[] volumes = {100, 80, 60, 40};
        ConfiguracaoMusical configuracao = new ConfiguracaoMusical(
                120, instrumentos, oitavas, volumes);

        instrumentos[0] = 50;
        oitavas[0] = 2;
        volumes[0] = 10;

        assertEquals(6, configuracao.getInstrumento(0));
        assertEquals(6, configuracao.getOitava(0));
        assertEquals(100, configuracao.getVolume(0));
    }

    @Test
    void deveRejeitarIndicesDeVozInvalidosEmTodosOsGetters() {
        ConfiguracaoMusical configuracao = ConfiguracaoMusical.configuracaoPadrao();
        int[] vozesInvalidas = {-1, 4};

        for (int voz : vozesInvalidas) {
            assertThrows(IllegalArgumentException.class, () -> configuracao.getInstrumento(voz));
            assertThrows(IllegalArgumentException.class, () -> configuracao.getOitava(voz));
            assertThrows(IllegalArgumentException.class, () -> configuracao.getVolume(voz));
        }
    }
}
