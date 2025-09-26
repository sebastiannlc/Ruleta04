package Vista;

import javax.swing.*;
import java.awt.*;

import Modelo.Ruleta; // Delegación al Controlador
import Modelo.Resultado;        // Delegación al Modelo Resultado

/**
 * Responsabilidad Única: Ser la Interfaz de Usuario (UI) para el juego.
 */
public class VentanaRuleta {

    private final JFrame frame = new JFrame("Ruleta Americana");
    private final JLabel lblUsuarioActual = new JLabel();
    private final JLabel lblNumeroGanador = new JLabel("Gire la Ruleta...");
    private final JLabel lblMontoApostar = new JLabel("Monto ($):");
    private final JTextField txtMonto = new JTextField(10);

    private final JRadioButton rbRojo = new JRadioButton("Rojo");
    private final JRadioButton rbNegro = new JRadioButton("Negro");
    private final JRadioButton rbPar = new JRadioButton("Par");
    private final JRadioButton rbImpar = new JRadioButton("Impar");
    private final ButtonGroup grupoApuestas = new ButtonGroup();

    private final JButton btnGirar = new JButton("GIRAR RULETA 🎰");
    private final JButton btnEstadisticas = new JButton("Ver Estadísticas");
    private final JButton btnVolver = new JButton("Volver al Menú");

    private final JTextArea areaHistorial = new JTextArea(10, 30);

    private final String nombreUsuario;
    private final VentanaMenu menuPrincipal;

    public VentanaRuleta(String nombreUsuario, VentanaMenu menuPrincipal) {
        this.nombreUsuario = nombreUsuario;
        this.menuPrincipal = menuPrincipal;
        frame.setTitle("Ruleta Americana - Jugador: " + nombreUsuario);

        configurarComponentes();
        agregarListeners();
    }

    private void configurarComponentes() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                menuPrincipal.mostrarVentana();
            }
        });

        setupPanelNorte();
        setupPanelCentral();
        setupPanelEste();

        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    private void setupPanelNorte() {
        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lblUsuarioActual.setText("Bienvenido, " + nombreUsuario + ". ¡A Jugar!");
        panelNorte.add(lblUsuarioActual);
        frame.add(panelNorte, BorderLayout.NORTH);
    }

    private void setupPanelCentral() {
        JPanel panelCentral = new JPanel(new GridLayout(5, 1, 5, 5));

        JPanel panelApuestas = new JPanel();
        panelApuestas.setBorder(BorderFactory.createTitledBorder("Tipo de Apuesta"));
        grupoApuestas.add(rbRojo); grupoApuestas.add(rbNegro);
        grupoApuestas.add(rbPar); grupoApuestas.add(rbImpar);
        panelApuestas.add(rbRojo); panelApuestas.add(rbNegro);
        panelApuestas.add(rbPar); panelApuestas.add(rbImpar);
        panelCentral.add(panelApuestas);

        JPanel panelMonto = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelMonto.add(lblMontoApostar);
        panelMonto.add(txtMonto);
        panelCentral.add(panelMonto);

        panelCentral.add(btnGirar);
        panelCentral.add(btnEstadisticas);
        panelCentral.add(btnVolver);

        frame.add(panelCentral, BorderLayout.WEST);
    }

    private void setupPanelEste() {
        JPanel panelEste = new JPanel(new BorderLayout());
        panelEste.setBorder(BorderFactory.createTitledBorder("Resultado y Historial"));

        lblNumeroGanador.setFont(new Font("Arial", Font.BOLD, 20));
        lblNumeroGanador.setHorizontalAlignment(SwingConstants.CENTER);
        panelEste.add(lblNumeroGanador, BorderLayout.NORTH);

        areaHistorial.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaHistorial);
        panelEste.add(scroll, BorderLayout.CENTER);

        frame.add(panelEste, BorderLayout.CENTER);
    }

    private void agregarListeners() {
        btnGirar.addActionListener(e -> jugarRondaGUI());
        // Llama al Modelo Resultado
        btnEstadisticas.addActionListener(e -> Resultado.mostrarEstadisticas());
        btnVolver.addActionListener(e -> volverAlMenu());
    }

    public void mostrarVentana() {
        frame.setVisible(true);
    }

    private void volverAlMenu() {
        frame.dispose();
        menuPrincipal.mostrarVentana();
    }

    private char obtenerTipoApuestaSeleccionado() {
        if (rbRojo.isSelected()) return 'R';
        if (rbNegro.isSelected()) return 'N';
        if (rbPar.isSelected()) return 'P';
        if (rbImpar.isSelected()) return 'I';
        return ' ';
    }

    private void jugarRondaGUI() {
        char tipoApuesta = obtenerTipoApuestaSeleccionado();
        double monto;

        try { monto = Double.parseDouble(txtMonto.getText()); }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Monto inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (tipoApuesta == ' ' || monto <= 0 || Resultado.historialSize >= Resultado.MAX_HISTORIAL) {
            JOptionPane.showMessageDialog(frame, "Error: Apuesta, monto positivo, o historial lleno.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numero = Ruleta.girar();
        boolean acierto = Ruleta.evaluarApuesta(numero, tipoApuesta);

        // Llama a Resultado para registrar
        Resultado.registrarResultado(numero, monto, acierto);
        actualizarGUI(numero, tipoApuesta, monto, acierto);
    }

    private void actualizarGUI(int numero, char tipo, double monto, boolean acierto) {
        double gananciaNeta = acierto ? monto * Resultado.PAGO_MULTIPLICADOR : -monto;
        String resultado = acierto ? "¡GANÓ!" : "PERDIÓ.";
        String color = Ruleta.esRojo(numero) ? "ROJO" : (numero == 0 ? "VERDE" : "NEGRO");

        lblNumeroGanador.setText("Ganador: " + numero + " (" + color + ")");

        String logEntry = String.format("Ronda #%d: Apuesta: %c | Ganó: %d (%s). Neta: $%.2f\n",
                Resultado.historialSize, tipo, numero, resultado, gananciaNeta);

        areaHistorial.append(logEntry);
    }
}