package Vista;

import Controlador.SessionController;
import Modelo.Resultado;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaHistorial {

    private final JFrame frame = new JFrame("Historial Personal");
    private final JTextArea areaHistorial = new JTextArea(20, 40);
    private final JButton btnVolver = new JButton("Volver al Menú");
    private final VentanaMenu menuAnterior;

    public VentanaHistorial(VentanaMenu menuAnterior) {
        this.menuAnterior = menuAnterior;
        configurarComponentes();
        cargarHistorial();
        agregarListeners();
    }

    private void configurarComponentes() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                menuAnterior.mostrarVentana();
            }
        });

        areaHistorial.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaHistorial);

        frame.add(scroll, BorderLayout.CENTER);

        JPanel panelSur = new JPanel();
        panelSur.add(btnVolver);
        frame.add(panelSur, BorderLayout.SOUTH);

        frame.pack();
    }

    private void agregarListeners() {
        btnVolver.addActionListener(e -> volverAlMenu());
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void volverAlMenu() {
        frame.dispose();
        menuAnterior.mostrarVentana();
    }

    private void cargarHistorial() {
        // La Vista simplemente solicita el String ya formateado.
        String historialTexto = SessionController.getInstancia().generarHistorialPersonalFormateado();

        areaHistorial.setText(historialTexto);
    }
}