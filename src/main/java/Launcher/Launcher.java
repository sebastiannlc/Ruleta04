package Launcher;

import Controlador.ResultadoController;
import Persistencia.RepositorioEnMemoria;
import Interfaces.IRepositorioResultados;
import Vista.VentanaLogin;
import javax.swing.SwingUtilities;

public class Launcher {

    public static void main(String[] args) {

        IRepositorioResultados repositorio = new RepositorioEnMemoria();
        ResultadoController controller = new ResultadoController(repositorio);
        // Iniciar la UI
        new VentanaLogin().mostrarVentana();
    }
}