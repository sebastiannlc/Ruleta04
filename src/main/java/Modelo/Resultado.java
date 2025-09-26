package Modelo;

import javax.swing.JOptionPane;

/**
 * Responsabilidad Única: Gestionar el historial de juego y calcular estadísticas (almacena los 'Resultados').
 */
public class Resultado {

    // Constantes y Estructuras de Datos
    public static final int MAX_HISTORIAL = 100;
    public static final double PAGO_MULTIPLICADOR = 1.0;

    public static int[] historialNumeros = new int[MAX_HISTORIAL];
    public static double[] historialMontos = new double[MAX_HISTORIAL];
    public static boolean[] historialAciertos = new boolean[MAX_HISTORIAL];
    public static int historialSize = 0;

    // --- Métodos de Historial ---

    public static void registrarResultado(int numero, double monto, boolean acierto) {
        if (historialSize >= MAX_HISTORIAL) {
            System.err.println("Historial lleno. No se pudo registrar la ronda.");
            return;
        }
        historialNumeros[historialSize] = numero;
        historialMontos[historialSize] = monto;
        historialAciertos[historialSize] = acierto;
        historialSize++;
    }

    // --- Métodos de Estadísticas ---

    public static void mostrarEstadisticas() {
        if (historialSize == 0) {
            JOptionPane.showMessageDialog(null, "No hay rondas jugadas para mostrar estadísticas.",
                    "Estadísticas", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        double totalApostado = calcularTotalApostado();
        long aciertos = calcularTotalAciertos();
        double gananciaNeta = calcularGananciaNeta(aciertos, totalApostado);

        imprimirReporte(aciertos, totalApostado, gananciaNeta);
    }

    public static double calcularTotalApostado() {
        double total = 0;
        for (int i = 0; i < historialSize; i++) {
            total += historialMontos[i];
        }
        return total;
    }

    public static long calcularTotalAciertos() {
        long aciertos = 0;
        for (int i = 0; i < historialSize; i++) {
            if (historialAciertos[i]) {
                aciertos++;
            }
        }
        return aciertos;
    }

    public static double calcularGananciaNeta(long aciertos, double totalApostado) {
        if (historialSize == 0) return 0.0;
        long perdidas = historialSize - aciertos;
        double montoPromedio = totalApostado / historialSize;

        double totalGanado = aciertos * montoPromedio * PAGO_MULTIPLICADOR;
        double totalPerdido = perdidas * montoPromedio;

        return totalGanado - totalPerdido;
    }

    public static void imprimirReporte(long aciertos, double totalApostado, double gananciaNeta) {
        double porcentajeAciertos = (double) aciertos / historialSize * 100;
        String signo = gananciaNeta >= 0 ? "GANANCIA" : "PÉRDIDA";

        String reporte = String.format(
                "*** 📊 Estadísticas Generales ***\n" +
                        "Rondas Jugadas: %d\n" +
                        "Total Apostado: $%.2f\n" +
                        "Porcentaje de Aciertos: %.2f%%\n" +
                        "Ganancia/Pérdida Neta: $%.2f (%s)",
                historialSize, totalApostado, porcentajeAciertos, Math.abs(gananciaNeta), signo
        );
        JOptionPane.showMessageDialog(null, reporte, "Reporte de Resultados", JOptionPane.INFORMATION_MESSAGE);
    }
}