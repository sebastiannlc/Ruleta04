package Controlador;

import Modelo.Resultado;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class ResultadoController {

    public static final int MAX_HISTORIAL = 100;
    // NUEVA CONSTANTE: Para apuestas simples (Rojo/Negro, Par/Impar) el pago es 1 a 1.
    public static final double PAGO_MULTIPLICADOR = 1.0;

    public static List<Resultado> historial = new ArrayList<>();

    public static void registrarResultado(Resultado resultado) {
        if (historial.size() >= MAX_HISTORIAL) {
            System.err.println("Historial lleno. No se pudo registrar la ronda.");
            return;
        }
        historial.add(resultado);
    }

    // Metodos para estadisticas
    public static void mostrarEstadisticas() {
        if (historial.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay rondas jugadas para mostrar estadísticas.",
                    "Estadísticas", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int totalRondas = historial.size();
        int rondasGanadas = contarRondasGanadas();
        int rondasPerdidas = totalRondas - rondasGanadas;
        double totalApostado = calcularTotalApostado();
        double gananciaNeta = calcularGananciaNeta();

        // CORRECCIÓN: El porcentaje de aciertos es (Ganadas / Totales) * 100
        double porcentajeAciertos = (double) rondasGanadas / totalRondas * 100;

        mostrarReporte(totalRondas, rondasGanadas, rondasPerdidas, totalApostado, gananciaNeta, porcentajeAciertos);
    }

    public static int contarRondasGanadas() {
        int ganadas = 0;
        for (Resultado resultado : historial) {
            if(resultado.isAcierto()) {
                ganadas++;
            }
        }
        return ganadas;
    }

    public static double calcularTotalApostado() {
        double total = 0;
        for (Resultado resultado : historial) {
            total += resultado.getMonto();
        }
        return total;
    }

    public static double calcularGananciaNeta() {
        double total = 0;
        for (Resultado resultado : historial) {
            total += resultado.getGanancia();
        }
        return total;
    }

    public static void mostrarReporte(int totalRondas, int rondasGanadas, int rondasPerdidas, double totalApostado, double gananciaNeta, double porcentajeAciertos) {
        String estado = gananciaNeta >= 0 ? "GANANCIA" : "PÉRDIDA";

        String reporte = String.format(
                "*** ESTADÍSTICAS DE JUEGO ***\n\n" +
                        "Rondas Totales: %d\n" +
                        "Rondas Ganadas: %d\n" +
                        "Rondas Perdidas: %d\n" +
                        "Porcentaje de Aciertos: %.1f%%\n\n" +
                        "Total Apostado: $%.2f\n" +
                        "Balance Final: $%.2f (%s)",
                totalRondas, rondasGanadas, rondasPerdidas, porcentajeAciertos, totalApostado, gananciaNeta, estado
        );
        JOptionPane.showMessageDialog(null, reporte, "Reporte de Resultados", JOptionPane.INFORMATION_MESSAGE);
    }
}