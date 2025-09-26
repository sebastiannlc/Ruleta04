package Launcher;

import javax.swing.SwingUtilities;
import Vista.VentanaLogin;

public class Launcher {
    /**
     * Razón: El metodo main solo inicia la aplicación GUI.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaLogin login = new VentanaLogin();
            login.mostrarVentana();
        });
    }
}