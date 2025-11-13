package Utilidades;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorPersistencia {

    public static void guardarDatos(Object objeto, String nombreArchivo) {
        try (FileOutputStream fos = new FileOutputStream(nombreArchivo);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(objeto);
            System.out.println("âœ… Datos guardados en: " + nombreArchivo);

        } catch (IOException e) {
            System.err.println("â?Œ Error al guardar datos en " + nombreArchivo + ": " + e.getMessage());
        }
    }

    /**
	 * 
	 * @param nombreArchivo
	 */
	@SuppressWarnings("unchecked")
    public static <T> List<T> cargarDatos(String nombreArchivo) {
        try (FileInputStream fis = new FileInputStream(nombreArchivo);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            List<T> datosCargados = (List<T>) ois.readObject();
            System.out.println("âœ… Datos cargados desde: " + nombreArchivo);
            return datosCargados;

        } catch (FileNotFoundException e) {
            System.out.println("â„¹ï¸? Archivo " + nombreArchivo + " no encontrado. Iniciando lista vacÃ­a.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("â?Œ Error al cargar datos: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}