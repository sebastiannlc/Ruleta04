package Launcher;

import javax.swing.SwingUtilities;
import Vista.VentanaLogin;

public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaLogin login = new VentanaLogin();
            login.mostrarVentana();
        });
    }
}