package Vista;

import javax.swing.*;
import Modelo.Usuario; // Importa Usuario
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

/**
 * Responsabilidad Única: Manejar la interfaz y la autenticación.
 */
public class VentanaLogin {

    public static final List<Usuario> USUARIOS = new ArrayList<>();

    private final JFrame frame = new JFrame("Login - Casino Black Cat");
    private final JLabel lblUsuario = new JLabel("Usuario:");
    private final JTextField txtUsuario = new JTextField(15);
    private final JLabel lblClave = new JLabel("Clave:");
    private final JPasswordField txtClave = new JPasswordField(15);
    private final JButton btnIngresar = new JButton("Ingresar");

    public VentanaLogin() {
        inicializarUsuarios();
        configurarVentana();
        agregarListeners();
    }

    private void inicializarUsuarios() {
        USUARIOS.add(new Usuario("seba", "1234", "Seba"));
        USUARIOS.add(new Usuario("dev", "pass", "Desarrollador"));
    }

    private void configurarVentana() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(lblUsuario);
        mainPanel.add(txtUsuario);
        mainPanel.add(lblClave);
        mainPanel.add(txtClave);
        mainPanel.add(btnIngresar);

        frame.add(mainPanel);
    }

    private void agregarListeners() {
        btnIngresar.addActionListener(e -> login());
    }

    public void mostrarVentana() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void login() {
        String user = txtUsuario.getText();
        String pass = new String(txtClave.getPassword());

        String nombreUsuario = validarCredenciales(user, pass);

        if (!nombreUsuario.isEmpty()) {
            mostrarExito(nombreUsuario);
            abrirMenuPrincipal(nombreUsuario);
        } else {
            mostrarError();
        }
    }

    private String validarCredenciales(String u, String p) {
        for (Usuario usuario : USUARIOS) {
            if (usuario.validarCredenciales(u, p)) {
                return usuario.getNombre();
            }
        }
        return "";
    }

    private void mostrarExito(String nombre) {
        String msg = "¡Bienvenido, " + nombre + "!";
        JOptionPane.showMessageDialog(frame, msg, "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError() {
        String msg = "Credenciales incorrectas. Intente de nuevo.";
        JOptionPane.showMessageDialog(frame, msg, "Error de Login", JOptionPane.ERROR_MESSAGE);
    }

    private void abrirMenuPrincipal(String nombreUsuario) {
        frame.dispose();
        VentanaMenu menu = new VentanaMenu(nombreUsuario);
        menu.mostrarVentana();
    }
}