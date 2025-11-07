package Modelo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Estadisticas {

    private final int totalJugadas;
    private final long victorias;
    private final double porcentajeVictorias;
    private final int rachaMaxima;
    private final char tipoMasJugado;

    /**
     * Constructor que recibe el historial y calcula todas las métricas.
     */
    public Estadisticas(List<Resultado> historial) {
        this.totalJugadas = historial.size();

        if (totalJugadas == 0) {
            this.victorias = 0;
            this.porcentajeVictorias = 0.0;
            this.rachaMaxima = 0;
            this.tipoMasJugado = '-'; // Indica sin datos
        } else {
            this.victorias = calcularVictorias(historial);
            this.porcentajeVictorias = calcularPorcentajeVictorias();
            this.rachaMaxima = calcularRachaMaxima(historial);
            this.tipoMasJugado = calcularTipoMasJugado(historial);
        }
    }

    private long calcularVictorias(List<Resultado> historial) {
        return historial.stream().filter(Resultado::isAcierto).count();
    }

    private double calcularPorcentajeVictorias() {
        if (totalJugadas == 0) return 0.0;
        return (double) victorias / totalJugadas * 100;
    }

    private int calcularRachaMaxima(List<Resultado> historial) {
        int rachaActual = 0;
        int maxRacha = 0;

        for (Resultado r : historial) {
            if (r.isAcierto()) {
                rachaActual++;
            } else {
                maxRacha = Math.max(maxRacha, rachaActual);
                rachaActual = 0;
            }
        }
        return Math.max(maxRacha, rachaActual);
    }

    private char calcularTipoMasJugado(List<Resultado> historial) {
        Map<Character, Long> conteo = historial.stream()
                .collect(Collectors.groupingBy(Resultado::getTipoApuesta, Collectors.counting()));

        return conteo.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse('-');
    }

    // --- Getters ---

    public int getTotalJugadas() {
        return totalJugadas;
    }

    public long getVictorias() {
        return victorias;
    }

    public double getPorcentajeVictorias() {
        return porcentajeVictorias;
    }

    public int getRachaMaxima() {
        return rachaMaxima;
    }

    public char getTipoMasJugado() {
        return tipoMasJugado;
    }
}