package Vista;

import javax.swing.*;
import java.awt.*;
import Controlador.ResultadoController;
import Controlador.RuletaController;
import Controlador.SessionController;
import Modelo.Ruleta;
import Modelo.Resultado;
import Modelo.ApuestaBase;
import Utilidades.ApuestaFactory;

public class VentanaRuleta {

    private final JFrame frame = new JFrame("Ruleta Americana");
    private final JLabel lblUsuarioActual = new JLabel();
    private final JLabel lblSaldoActual = new JLabel();
    private final JLabel lblNumeroGanador = new JLabel("Gire la Ruleta...");
    private final JLabel lblMontoApostar = new JLabel("Monto ($):");
    private final JTextField txtMonto = new JTextField(10);

    private final JRadioButton rbRojo = new JRadioButton("Rojo");
    private final JRadioButton rbNegro = new JRadioButton("Negro");
    private final JRadioButton rbPar = new JRadioButton("Par");
    private final JRadioButton rbImpar = new JRadioButton("Impar");
    private final ButtonGroup grupoApuestas = new ButtonGroup();

    private final JButton btnGirar = new JButton("GIRAR RULETA 🎰");
    private final JButton btnEstadisticas = new JButton("Ver Estadísticas Globales");
    private final JButton btnVolver = new JButton("Volver al Menú");

    private final JTextArea areaHistorial = new JTextArea(10, 30);

    private final String nombreUsuario;
    private final VentanaMenu menuPrincipal;

    private final RuletaController ruletaController = new RuletaController();

    public VentanaRuleta(String nombreUsuario, VentanaMenu menuPrincipal) {
        this.nombreUsuario = nombreUsuario;
        this.menuPrincipal = menuPrincipal;

        frame.setTitle("Ruleta Americana - Jugador: " + nombreUsuario);

        configurarComponentes();
        agregarListeners();
        actualizarInfoUsuario();
    }

    private void configurarComponentes() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                menuPrincipal.mostrarVentana();
            }
        });

        setupPanelNorte();
        setupPanelControl();
        setupPanelHistorial();

        frame.pack();
    }

    private void setupPanelNorte() {
        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        lblUsuarioActual.setText("Bienvenido, " + nombreUsuario + ".");

        panelNorte.add(lblUsuarioActual);
        panelNorte.add(lblSaldoActual);

        frame.add(panelNorte, BorderLayout.NORTH);
    }

    private void setupPanelControl() {
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Panel de Apuestas
        JPanel panelApuestas = new JPanel();
        panelApuestas.setBorder(BorderFactory.createTitledBorder("Tipo de Apuesta"));
        grupoApuestas.add(rbRojo); grupoApuestas.add(rbNegro);
        grupoApuestas.add(rbPar); grupoApuestas.add(rbImpar);
        panelApuestas.add(rbRojo); panelApuestas.add(rbNegro);
        panelApuestas.add(rbPar); panelApuestas.add(rbImpar);
        panelCentral.add(panelApuestas);
        panelCentral.add(Box.createVerticalStrut(10));

        // 2. Panel de Monto
        JPanel panelMonto = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelMonto.add(lblMontoApostar);
        panelMonto.add(txtMonto);
        panelCentral.add(panelMonto);
        panelCentral.add(Box.createVerticalStrut(20));

        // 3. Botones de Acción
        panelCentral.add(btnGirar);
        panelCentral.add(Box.createVerticalStrut(10));
        panelCentral.add(btnEstadisticas);
        panelCentral.add(Box.createVerticalStrut(10));
        panelCentral.add(btnVolver);

        btnGirar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEstadisticas.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);


        frame.add(panelCentral, BorderLayout.WEST);
    }

    private void setupPanelHistorial() {
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

    private void actualizarInfoUsuario() {
        if (SessionController.getInstancia().getUsuarioActual() != null) {
            double saldo = SessionController.getInstancia().getUsuarioActual().getSaldo();
            lblSaldoActual.setText(String.format("Saldo: $%.2f", saldo));
        }
    }


    private void agregarListeners() {
        btnGirar.addActionListener(e -> jugarRondaGUI());
        btnEstadisticas.addActionListener(e -> ResultadoController.mostrarEstadisticas());
        btnVolver.addActionListener(e -> volverAlMenu());
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void volverAlMenu() {
        frame.dispose();
        menuPrincipal.mostrarVentana();
    }

    private char obtenerEtiquetaSeleccionada() {
        if (rbRojo.isSelected()) return 'R';
        if (rbNegro.isSelected()) return 'N';
        if (rbPar.isSelected()) return 'P';
        if (rbImpar.isSelected()) return 'I';
        return 'X';
    }

    private void jugarRondaGUI() {
        double monto;

        try {
            monto = Double.parseDouble(txtMonto.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Monto inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        char etiqueta = obtenerEtiquetaSeleccionada();

        if (etiqueta == 'X' || monto <= 0) {
            JOptionPane.showMessageDialog(frame, "Error: Seleccione apuesta y monto positivo.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double saldoActual = SessionController.getInstancia().getUsuarioActual().getSaldo();
        if  (monto > saldoActual) {
            JOptionPane.showMessageDialog(frame, "Saldo insuficiente para realizar la apuesta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            ApuestaBase apuesta = ApuestaFactory.crearApuesta(etiqueta, monto);

            Resultado resultado = ruletaController.jugarRonda(apuesta);

            actualizarGUI(resultado);
            actualizarInfoUsuario();

        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error de Juego", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, "Error en la apuesta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarGUI(Resultado resultado) {
        String color = Ruleta.getColor(resultado.getNumero());
        lblNumeroGanador.setText("Ganador: " + resultado.getNumero() + " (" + color + ")");

        String logEntry = ruletaController.generarLogRonda(resultado);

        areaHistorial.append(logEntry);
    }
}