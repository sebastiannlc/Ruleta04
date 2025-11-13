package Controlador;

import Interfaces.IRepositorioResultados;
import Modelo.Resultado;
import Modelo.Estadisticas;
import java.util.List;
import javax.swing.JOptionPane;

public class ResultadoController {

    private static IRepositorioResultados repositorioActivo;
    private static IRepositorioResultados repositorioArchivo;
    private static IRepositorioResultados repositorioMemoria;

    //Constructor
    public ResultadoController(IRepositorioResultados archivoRepo, IRepositorioResultados memoriaRepo) {
        ResultadoController.repositorioArchivo = archivoRepo;
        ResultadoController.repositorioMemoria = memoriaRepo;

        if (inicializarRepositorio()) {
            System.out.println("Controlador iniciado con RepositorioArchivo.");
        } else {
            System.out.println("FALLBACK ACTIVADO: Usando RepositorioEnMemoria.");
        }
    }

    private boolean inicializarRepositorio() {
        try {
            repositorioArchivo.obtenerHistorialGlobal();
            repositorioActivo = repositorioArchivo;
            return true;
        } catch (RuntimeException e) {
            System.err.println("Error crítico al inicializar RepositorioArchivo: " + e.getMessage());
            repositorioActivo = repositorioMemoria;
            return false;
        }
    }

    public static void registrarResultado(Resultado resultado) {
        if (repositorioActivo == null) {
            System.err.println("Error: Repositorio no inicializado.");
            return;
        }

        if (repositorioActivo == repositorioArchivo) {
            try {
                repositorioActivo.agregarResultado(resultado);
            } catch (RuntimeException e) {
                System.err.println("FALLBACK: Error al escribir en archivo. Cambiando a RepositorioEnMemoria.");
                repositorioActivo = repositorioMemoria;
                 // Se intenta guardar una vez más.
                repositorioActivo.agregarResultado(resultado);
            }
        } else {
            repositorioActivo.agregarResultado(resultado);
        }
    }

    public static void mostrarEstadisticas() {

        if (repositorioActivo == null) {
            JOptionPane.showMessageDialog(null, "El sistema de repositorio no está activo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Resultado> historial = repositorioActivo.obtenerHistorialGlobal();

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