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
        List<Resultado> historial = SessionController.getInstancia().getHistorialPersonal();
        if (historial.isEmpty()) {
            areaHistorial.setText("No hay resultados registrados en tu historial.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-8s %-10s %-10s\n", "Ronda", "Apuesta", "Número", "Ganancia Neta"));
        sb.append("-------------------------------------------\n");

        for (int i = 0; i < historial.size(); i++) {
            Resultado r = historial.get(i);
            String acierto = r.isAcierto() ? " (GANÓ)" : "";
            sb.append(String.format("%-10d %-8c %-10d %-10.2f %s\n",
                    i + 1,
                    r.getTipoApuesta(),
                    r.getNumero(),
                    r.getGanancia(),
                    acierto
            ));
        }

        areaHistorial.setText(sb.toString());
    }
}