package Modelo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Estadisticas {

    private final List<Resultado> historial;

    public Estadisticas(List<Resultado> historial) {
        this.historial = historial;
    }

    public int getTotalJugadas() {
        return historial.size();
    }

    public long getVictorias() {
        return historial.stream().filter(Resultado::isAcierto).count();
    }

    public double getPorcentajeVictorias() {
        if (getTotalJugadas() == 0) return 0.0;
        return (getVictorias() / (double) getTotalJugadas()) * 100;
    }

    public String getTipoMasJugado() {
        if (historial.isEmpty()) return "N/A";

        Map<Character, Long> conteo = historial.stream()
                .collect(Collectors.groupingBy(Resultado::getTipoApuesta, Collectors.counting()));

        return conteo.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> e.getKey().toString())
                .orElse("N/A");
    }

    public int getRachaMaxima() {
        int racha = 0;
        int maxRacha = 0;
        for (Resultado r : historial) {
            if (r.isAcierto()) {
                racha++;
            } else {
                maxRacha = Math.max(maxRacha, racha);
                racha = 0;
            }
        }
        return Math.max(maxRacha, racha);
    }
}