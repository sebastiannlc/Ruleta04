package Utilidades;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorPersistencia {

    public static void guardarDatos(Object objeto, String nombreArchivo) {
        try (FileOutputStream fos = new FileOutputStream(nombreArchivo);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(objeto);
            System.out.println("✅ Datos guardados en: " + nombreArchivo);

        } catch (IOException e) {
            System.err.println("�?� Error al guardar datos en " + nombreArchivo + ": " + e.getMessage());
        }
    }

	@SuppressWarnings("unchecked")
    public static <T> List<T> cargarDatos(String nombreArchivo) {
        try (FileInputStream fis = new FileInputStream(nombreArchivo);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            List<T> datosCargados = (List<T>) ois.readObject();
            System.out.println("✅ Datos cargados desde: " + nombreArchivo);
            return datosCargados;

        } catch (FileNotFoundException e) {
            System.out.println("ℹ�? Archivo " + nombreArchivo + " no encontrado. Iniciando lista vacía.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("�?� Error al cargar datos: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}