package Vista;

import javax.swing.*;
import java.awt.*;

import Controlador.ResultadoController;
import Controlador.RuletaController;
import Modelo.Ruleta;
import Modelo.Resultado;
import Modelo.TipoApuesta; // Importación del Enum

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

    private final RuletaController ruletaController = new RuletaController();

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
        btnEstadisticas.addActionListener(e -> ResultadoController.mostrarEstadisticas());
        btnVolver.addActionListener(e -> volverAlMenu());
    }

    public void mostrarVentana() {
        frame.setVisible(true);
    }

    private void volverAlMenu() {
        frame.dispose();
        menuPrincipal.mostrarVentana();
    }

    private TipoApuesta obtenerTipoApuestaSeleccionado() {
        if (rbRojo.isSelected()) return TipoApuesta.ROJO;
        if (rbNegro.isSelected()) return TipoApuesta.NEGRO;
        if (rbPar.isSelected()) return TipoApuesta.PAR;
        if (rbImpar.isSelected()) return TipoApuesta.IMPAR;
        return null;
    }

    private void jugarRondaGUI() {
        TipoApuesta tipoApuesta = obtenerTipoApuestaSeleccionado();
        double monto;

        try {
            monto = Double.parseDouble(txtMonto.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Monto inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (tipoApuesta == null || monto <= 0 || ResultadoController.historial.size() >= ResultadoController.MAX_HISTORIAL) {
            JOptionPane.showMessageDialog(frame, "Error: Seleccione apuesta y monto positivo. (Historial lleno si supera " + ResultadoController.MAX_HISTORIAL + ").", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Resultado resultado = ruletaController.jugarRonda(monto, tipoApuesta);

        actualizarGUI(
                resultado.getNumero(),
                resultado.getTipoApuesta(),
                resultado.getMonto(),
                resultado.isAcierto(),
                resultado.getGanancia()
        );
    }

    private void actualizarGUI(int numero, char tipo, double monto, boolean acierto, double gananciaNeta) {
        String resultadoTexto = acierto ? "¡GANÓ!" : "PERDIÓ.";
        String color = Ruleta.esRojo(numero) ? "ROJO" : (numero == 0 ? "VERDE" : "NEGRO");

        lblNumeroGanador.setText("Ganador: " + numero + " (" + color + ")");

        String logEntry = String.format("Ronda #%d: Apuesta: %c | Salió: %d (%s). Neta: $%.2f\n",
                ResultadoController.historial.size(), tipo, numero, resultadoTexto, gananciaNeta);

        areaHistorial.append(logEntry);
    }
}