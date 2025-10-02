package Vista;

import javax.swing.*;
import Modelo.Usuario;
import java.awt.*;

import Controlador.SessionController;

public class VentanaRegistro {

    private static final double SALDO_INICIAL = 1000.0;

    private final JFrame frame = new JFrame("Registro de Nuevo Jugador");

    private final JLabel lblUsuario = new JLabel("Nombre de Usuario:");
    private final JTextField txtUsuario = new JTextField(15);
    private final JLabel lblNombre = new JLabel("Nombre Completo:");
    private final JTextField txtNombre = new JTextField(15);
    private final JLabel lblClave = new JLabel("Contraseña:");
    private final JPasswordField txtClave = new JPasswordField(15);
    private final JButton btnRegistrar = new JButton("Registrar y Jugar");
    private final JButton btnVolver = new JButton("Volver al Login");

    public VentanaRegistro() {
        configurarVentana();
        agregarListeners();
    }

    private void configurarVentana() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        formPanel.add(lblNombre);
        formPanel.add(txtNombre);
        formPanel.add(lblUsuario);
        formPanel.add(txtUsuario);
        formPanel.add(lblClave);
        formPanel.add(txtClave);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(btnVolver);
        buttonPanel.add(btnRegistrar);

        frame.setLayout(new BorderLayout());
        frame.add(formPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void agregarListeners() {
        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnVolver.addActionListener(e -> volverALogin());
    }

    public void mostrarVentana() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void volverALogin() {
        frame.dispose();
        SwingUtilities.invokeLater(() -> new VentanaLogin().mostrarVentana());
    }

    private void registrarUsuario() {
        String username = txtUsuario.getText().trim();
        String password = new String(txtClave.getPassword());
        String nombre = txtNombre.getText().trim();

        if (username.isEmpty() || password.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Todos los campos son obligatorios.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (VentanaLogin.usuarioExiste(username)) {
            JOptionPane.showMessageDialog(frame, "El nombre de usuario ya está en uso.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario nuevoUsuario = new Usuario(username, password, nombre, SALDO_INICIAL);

        VentanaLogin.USUARIOS.add(nuevoUsuario);

        SessionController.getInstancia().iniciarSesion(nuevoUsuario);

        JOptionPane.showMessageDialog(frame, String.format("¡Registro exitoso! Bienvenido, %s. Saldo inicial: $%.2f", nombre, SALDO_INICIAL), "Éxito", JOptionPane.INFORMATION_MESSAGE);

        frame.dispose();

        VentanaMenu menu = new VentanaMenu(nombre);
        menu.mostrarVentana();
    }
}