package Controlador;

import Interfaces.IRepositorioResultados;
import Modelo.Resultado;
import Modelo.Estadisticas;
import java.util.List;
import javax.swing.JOptionPane;

public class ResultadoController {

    private static IRepositorioResultados repositorio;

    private static final String ARCHIVO_HISTORIAL = "historial.dat";
    public static final int MAX_HISTORIAL = 20;

    //Constructor
    public ResultadoController(IRepositorioResultados repositorio) {
        ResultadoController.repositorio = repositorio;
    }

    public static void registrarResultado(Resultado resultado) {
        if (repositorio == null) {
            System.err.println("Error: Repositorio no inicializado.");
        }
        repositorio.agregarResultado(resultado);
    }

    public static void mostrarEstadisticas() {

        if (repositorio == null) {
            JOptionPane.showMessageDialog(null, "El sistema de repositorio no está activo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Resultado> historial = repositorio.obtenerHistorialGlobal();

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