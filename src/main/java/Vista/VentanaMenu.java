package Vista;

import javax.swing.*;
import java.awt.*;

public class VentanaMenu {

    private final JFrame frame = new JFrame("Menú Principal");
    private final String nombreUsuario;

    private final JButton btnJugar = new JButton("Jugar Ruleta");
    private final JButton btnHistorial = new JButton("Ver Historial");
    private final JButton btnSalir = new JButton("Cerrar Sesión");

    public VentanaMenu(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        frame.setTitle("Menú - Jugador: " + nombreUsuario);
        configurarComponentes();
        agregarListeners();
    }

    private void configurarComponentes() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel lblBienvenida = new JLabel("¡Hola, " + nombreUsuario + "!");
        lblBienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));

        btnJugar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnHistorial.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(lblBienvenida);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(btnJugar);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnHistorial);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnSalir);

        frame.add(panel);
        frame.pack();
    }

    private void agregarListeners() {
        btnJugar.addActionListener(e -> mostrarVentanaRuleta());
        btnHistorial.addActionListener(e -> mostrarVentanaHistorial());
        btnSalir.addActionListener(e -> cerrarSesion());
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void mostrarVentanaRuleta() {
        frame.dispose();
        new VentanaRuleta(nombreUsuario, this).mostrarVentana();
    }

    private void mostrarVentanaHistorial() {
        frame.dispose();
        new VentanaHistorial(this).mostrarVentana();
    }

    private void cerrarSesion() {
        // Guarda los datos antes de salir si es necesario (aunque ya lo hacen Login/Registro/Resultados)
        frame.dispose();
        new VentanaLogin().mostrarVentana();
    }
}