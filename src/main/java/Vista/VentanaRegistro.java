package Vista;

import Modelo.Usuario;
import Controlador.SessionController;
import javax.swing.*;
import java.awt.*;

public class VentanaRegistro {

    private final JFrame frame = new JFrame("Registro de Usuario");
    private final JTextField txtUsername = new JTextField(15);
    private final JPasswordField txtPassword = new JPasswordField(15);
    private final JTextField txtNombre = new JTextField(15);
    private final JButton btnRegistrar = new JButton("Registrar");
    private final JButton btnVolver = new JButton("Volver al Login");

    public VentanaRegistro() {
        configurarComponentes();
        agregarListeners();
    }

    private void configurarComponentes() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Nombre Completo:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Usuario (Login):"));
        panel.add(txtUsername);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtPassword);

        panel.add(btnVolver);
        panel.add(btnRegistrar);

        frame.add(panel);
        frame.pack();
    }

    private void agregarListeners() {
        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnVolver.addActionListener(e -> volverAlLogin());
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void volverAlLogin() {
        frame.dispose();
        new VentanaLogin().mostrarVentana();
    }

    private void registrarUsuario() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String nombre = txtNombre.getText();

        if (username.isEmpty() || password.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (usuarioExiste(username)) {
            JOptionPane.showMessageDialog(frame, "Ese nombre de usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario nuevoUsuario = new Usuario(username, password, nombre);
        VentanaLogin.USUARIOS.add(nuevoUsuario);

        // **IMPORTANTE:** Persistencia de usuarios actualizada
        VentanaLogin.guardarUsuarios();

        SessionController.getInstancia().iniciarSesion(nuevoUsuario);

        JOptionPane.showMessageDialog(frame, "Registro exitoso. ¡Bienvenido!", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        frame.dispose();
        new VentanaMenu(nombre).mostrarVentana();
    }

    private boolean usuarioExiste(String username) {
        return VentanaLogin.USUARIOS.stream()
                .anyMatch(u -> u.getUsername().equals(username));
    }
}