package Controlador;

import Utilidades.GestorPersistencia;
import Modelo.Resultado;
import Modelo.Estadisticas;
import java.util.List;
import javax.swing.JOptionPane;

public class ResultadoController {

    private static final String ARCHIVO_HISTORIAL = "historial.dat";
    public static final int MAX_HISTORIAL = 20;

    // Cargar historial persistente al inicio
    public static final List<Resultado> historial =
            GestorPersistencia.cargarDatos(ARCHIVO_HISTORIAL);

    public static void registrarResultado(Resultado resultado) {
        if (historial.size() < MAX_HISTORIAL) {
            historial.add(resultado);
        }
        guardarHistorial();
    }

    private static void guardarHistorial() {
        GestorPersistencia.guardarDatos(historial, ARCHIVO_HISTORIAL);
    }

    public static void mostrarEstadisticas() {
        Estadisticas stats = new Estadisticas(historial);

        StringBuilder sb = new StringBuilder();
        sb.append("--- Estadísticas Globales ---\n");
        sb.append("Total de rondas jugadas: ").append(stats.getTotalJugadas()).append("\n");
        sb.append("Victorias: ").append(stats.getVictorias()).append("\n");
        sb.append(String.format("Porcentaje de victorias: %.2f%%\n", stats.getPorcentajeVictorias()));
        sb.append("Racha Máxima: ").append(stats.getRachaMaxima()).append(" aciertos consecutivos\n");
        sb.append("Tipo de Apuesta más jugado: ").append(stats.getTipoMasJugado()).append("\n");

        JOptionPane.showMessageDialog(null, sb.toString(), "Estadísticas", JOptionPane.INFORMATION_MESSAGE);
    }
}