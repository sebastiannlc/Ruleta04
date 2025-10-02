package Vista;

import javax.swing.*;
import Modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

import Controlador.SessionController;

public class VentanaLogin {

    public static final List<Usuario> USUARIOS = new ArrayList<>();

    private final JFrame frame = new JFrame("Login - Casino Black Cat");
    private final JLabel lblUsuario = new JLabel("Usuario:");
    private final JTextField txtUsuario = new JTextField(15);
    private final JLabel lblClave = new JLabel("Clave:");
    private final JPasswordField txtClave = new JPasswordField(15);
    private final JButton btnIngresar = new JButton("Ingresar");
    private final JButton btnRegistrar = new JButton("Registrarse");

    public VentanaLogin() {
        if (USUARIOS.isEmpty()) {
            inicializarUsuarios();
        }
        configurarVentana();
        agregarListeners();
    }

    private void inicializarUsuarios() {
        // Usa el constructor con saldo
        USUARIOS.add(new Usuario("seba", "1234", "Seba", 1500.0));
        USUARIOS.add(new Usuario("dev", "pass", "Desarrollador", 5000.0));
    }

    private void configurarVentana() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.add(lblUsuario);
        inputPanel.add(txtUsuario);
        inputPanel.add(lblClave);
        inputPanel.add(txtClave);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnIngresar);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);

        frame.add(mainPanel);
    }

    private void agregarListeners() {
        btnIngresar.addActionListener(e -> login());
        btnRegistrar.addActionListener(e -> abrirVentanaRegistro());
    }

    public void mostrarVentana() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void abrirVentanaRegistro() {
        frame.dispose();
        new VentanaRegistro().mostrarVentana();
    }

    private void login() {
        String user = txtUsuario.getText();
        String pass = new String(txtClave.getPassword());

        Usuario usuarioLogueado = validarCredenciales(user, pass);

        if (usuarioLogueado != null) {
            // Inicia la sesión
            SessionController.getInstancia().iniciarSesion(usuarioLogueado);

            mostrarExito(usuarioLogueado.getNombre());
            frame.dispose();
            abrirMenuPrincipal(); // Llama a la navegación
        } else {
            mostrarError();
        }
    }

    private Usuario validarCredenciales(String u, String p) {
        for (Usuario usuario : USUARIOS) {
            if (usuario.validarCredenciales(u, p)) {
                return usuario;
            }
        }
        return null;
    }

    private void mostrarExito(String nombre) {
        String msg = "¡Bienvenido, " + nombre + "!";
        JOptionPane.showMessageDialog(frame, msg, "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError() {
        String msg = "Credenciales incorrectas. Intente de nuevo.";
        JOptionPane.showMessageDialog(frame, msg, "Error de Login", JOptionPane.ERROR_MESSAGE);
    }

    private void abrirMenuPrincipal() {
        String nombreUsuario = SessionController.getInstancia().getNombreUsuario();
        VentanaMenu menu = new VentanaMenu(nombreUsuario);
        menu.mostrarVentana();
    }

    public static boolean usuarioExiste(String username) {
        for (Usuario usuario : USUARIOS) {
            if (usuario.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }
}