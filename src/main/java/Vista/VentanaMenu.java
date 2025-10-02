package Vista;

import javax.swing.*;
import java.awt.*;

public class VentanaMenu {

    private final JFrame frame = new JFrame("Menú Principal");
    private final String nombreUsuario;

    private final JButton btnJugar = new JButton("Jugar a la Ruleta 🎰");
    private final JButton btnCerrarSesion = new JButton("Cerrar Sesión");

    public VentanaMenu(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        frame.setTitle("Menú Principal - " + nombreUsuario);
        configurarVentana();
        agregarListeners();
    }

    private void configurarVentana() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 1, 10, 10));

        frame.add(new JLabel("Bienvenido, " + nombreUsuario, SwingConstants.CENTER));
        frame.add(btnJugar);
        frame.add(btnCerrarSesion);

        frame.setSize(300, 200);
    }

    private void agregarListeners() {
        btnJugar.addActionListener(e -> abrirVentanaJuego());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void abrirVentanaJuego() {
        frame.setVisible(false);
        VentanaRuleta juego = new VentanaRuleta(nombreUsuario, this);
        juego.mostrarVentana();
    }

    private void cerrarSesion() {
        frame.dispose();
        JOptionPane.showMessageDialog(null, "Sesión cerrada con éxito.");
        SwingUtilities.invokeLater(() -> new VentanaLogin().mostrarVentana());
    }
}